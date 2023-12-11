import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun checkUserLoggedIn() {
        if (firebaseAuth.currentUser != null) {
            loginResult.postValue(true)
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                loginResult.postValue(true)
            }
            .addOnFailureListener { error ->
                errorMessage.postValue(error.localizedMessage)
            }
    }
}
