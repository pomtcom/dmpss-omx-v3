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
    }
    

}




