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
        print('mysecret is ' + mysecret) ;
    }
    stage('Checkout test'){
        print('test checkout');
        checkout scm ;
        print('checkout is completed');
    }
    stage('Read .yaml test'){
        mydata = readYaml file: "secrettest.yml"

        print('test read value is ' + mydata.type);
    }
    stage('test write ymal'){
        def amap = ['something': 'my datas','size': 3, 'isEmpty': false]
        writeYaml file: 'datas.yaml', data: amap
    }
    stage('test yaml2'){
        mydata = readYaml file: "secrettest.yml"
        //modify
        mydata.info = "b" ;
        mydata.data.TESTKEY2 = "key2" ;
        mydata.data.TESTKEY3 = "key3" ;
        writeYaml file: 'newtest.yaml', data: mydata ;
    }
    

}




