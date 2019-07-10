// @Library('vault-library') import com.company.TestTest
// @Library('vault-library') import com.company.ClassTest

@Library('vault-library') import com.truedigital.Vault

node {
     
    // stage('Test credential'){
    //     withCredentials([string(credentialsId: 'VaultToken', variable: 'myassignsecret')]) {
    //           echo "My password is '${myassignsecret}'!"
    //           print 'myGlobalVar is ' + myGlobalVar ;
    //           myGlobalVar = myassignsecret ;
    //           print 'myGlobalVarInSecond is ' + myGlobalVar ;
    //     }
    // }

    stage('Test Get Secret from Vault'){
        def vault = new Vault(this);
        vault.init();
        def mysecret = vault.getSecret('mysql', 'mysql-omx-instance1');
        // print('mysecret is ' + mysecret) ;

        // vault.checkOutSecretTemplate();
        // vault.putSecretTest('KEYZXZXXZX','cGFzc3dvcmQ');
        vault.writeSecretYaml();
    }
    stage('OC test'){
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'OC_CREDENTIAL', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
            // sh "oc login --username ${env.USERNAME} --password ${env.PASSWORD} ${env.OC_URL_NonProd} --insecure-skip-tls-verify"
            print('username is ' + USERNAME);
            print('password is ' + PASSWORD);
        }
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




