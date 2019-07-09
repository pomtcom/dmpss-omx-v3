// @Library('vault-library') import com.company.TestTest
// @Library('vault-library') import com.company.ClassTest

@Library('vault-library') import com.truedigital.Vault

def getSecretFromVault(String environment='alpha', String[] key){
    print ('environment is ' + environment);
    print('key is ' + key) ;
}

def getSecretFromVault2(String environment, String[] key){
    print('environment is ' + environment);
    print('key is ' + key) ;
}

def test(){
    print ('THIS IS TEST')
}

def arrayTest(String[] test){
    print('arrayTest is ' + test) ;
}

node {
    
    def myGlobalVar = 'TESTVAR';
    
    stage('Demo') {
        echo 'env.JOB_BASE_NAME is ' 
        echo env.JOB_BASE_NAME
        echo 'Hello World' ;
        // sayHello 'Dave';
        // String[] testKey = ["A", "B", "C"];
        // print 'testKey is ' + testKey;
        
        // arrayTest(testKey);
        // getSecretFromVault2('prod',testKey);
        // getSecretFromVault('prod', testKey) ;
        // test();
        // vaultGetSecret('VAULT');
        
        print 'test print after called library' ;
        
    }
    stage('End stage'){
        print 'this is end stage';
    }
    
    stage('object test'){
       print 'object test' ;
    //   def notification = new ClassTest(this);
    //   notification.testInt = 3 ;
    //   teststring = notification.method1();
    //   print('teststring is ' + teststring) ;
        // def testTest = new TestTest(this);

        
    
        def vault = new Vault(this);
        vault.init();
        // print('vault role_token is ' + vault.role_token);
        def mysecret = vault.getSecret('mysql', 'mysql-omx-instance1');
        print('mysecret is ' + mysecret) ;
        // print('role_token is ' + vault.role_token);
    
    }
    
    // stage('Test credential'){
    //     withCredentials([string(credentialsId: 'VaultToken', variable: 'myassignsecret')]) {
    //           echo "My password is '${myassignsecret}'!"
    //           print 'myGlobalVar is ' + myGlobalVar ;
    //           myGlobalVar = myassignsecret ;
    //           print 'myGlobalVarInSecond is ' + myGlobalVar ;
    //     }
    // }
}




