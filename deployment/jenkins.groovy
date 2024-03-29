// @Library('vault-library') import com.company.TestTest
// @Library('vault-library') import com.company.ClassTest

@Library('vault-library') import com.truedigital.Vault

def microservice_name = env.JOB_BASE_NAME ;
def docker_image = 'pomtcom/simplejsenv1:latest' ;

node {
     def vault = new Vault(this);
    // stage('Test credential'){
    //     withCredentials([string(credentialsId: 'VaultToken', variable: 'myassignsecret')]) {
    //           echo "My password is '${myassignsecret}'!"
    //           print 'myGlobalVar is ' + myGlobalVar ;
    //           myGlobalVar = myassignsecret ;
    //           print 'myGlobalVarInSecond is ' + myGlobalVar ;
    //     }
    // }

    stage('Get Secret from Vault'){
        
        vault.init();
        vault.getSecret('mysql', 'MYSQL_OMX_INSTANCE1');
        vault.getSecret('mysql', 'MYSQL_OMX_INSTANCE2');
        vault.writeSecretYaml();
    }
    stage('OC login'){
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'OC_CREDENTIAL', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
            // sh "oc login --username ${env.USERNAME} --password ${env.PASSWORD} ${env.OC_URL_NonProd} --insecure-skip-tls-verify"
            print('testing login');
            sh "oc login --username ${USERNAME} --password ${PASSWORD} https://ose2-np.dmp.true.th:8443 --insecure-skip-tls-verify"
            print('login is completed');
        }
    }
    stage('OC write secret'){
        // print('SECRET_TEMPLATE_YAML is ' + vault.secretFileName);
        
        try {
            sh "oc create -f ${vault.secretFileName}" ;
        } catch (Exception e) {
            print('secret may already existed, try to replace secret instead with openshift message ' + e) ;
            sh "oc replace -f ${vault.secretFileName}" ;
        }
    }
    stage('OC check new application'){
        try{
            sh "oc new-app --docker-image=${docker_image} --name=${microservice_name}"
        }catch (Exception e){
            print('application already created ');
            print('oc message : ' + e);
        }
    }
    stage('OC set secret'){
        sh "oc set env --from=secret/${vault.secretOCName} dc/${microservice_name}"
    }
    stage('OC start deployment'){
        sh "oc deploy --latest dc/${microservice_name}"
    }

    // stage('Checkout test'){
    //     print('test checkout');

        // checkout([
        //     $class: 'GitSCM', 
        //     branches: [[name: '*/master']], 
        //     doGenerateSubmoduleConfigurations: false, 
        //     extensions: [], 
        //     submoduleCfg: [], 
        //     userRemoteConfigs: [[url: 'https://github.com/pomtcom/dmpss-omx-v3.git']]])
        // print('checkout SCM is completed (version2)');
    
        // print('checkout is completed');
    // }
    // stage('Read .yaml test'){
    //     mydata = readYaml file: "secrettest.yml"

    //     print('test read value is ' + mydata.type);
    // }
    // stage('test write ymal'){
    //     def amap = ['something': 'my datas','size': 3, 'isEmpty': false]
    //     writeYaml file: 'datas.yaml', data: amap
    // }
    // stage('test yaml2'){
    //     mydata = readYaml file: "secrettest.yml"
    //     //modify
    //     mydata.info = "b" ;
    //     mydata.data.TESTKEY2 = "key2" ;
    //     mydata.data.TESTKEY3 = "key3" ;
    //     writeYaml file: 'newtest.yaml', data: mydata ;
    // }
    

}




