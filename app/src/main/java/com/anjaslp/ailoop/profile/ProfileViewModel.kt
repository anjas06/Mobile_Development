import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val userFullName: MutableLiveData<String> = MutableLiveData()
    val userEmail: MutableLiveData<String> = MutableLiveData()
    val logoutResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserInfo() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            userFullName.postValue(firebaseUser.displayName)
            userEmail.postValue(firebaseUser.email)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        logoutResult.postValue(true)
    }
}
