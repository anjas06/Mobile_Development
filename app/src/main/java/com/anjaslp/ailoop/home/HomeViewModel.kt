import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getGreetingMessage(): String {
        val firebaseUser = firebaseAuth.currentUser
        return if (firebaseUser != null) {
            "Hi, ${firebaseUser.displayName}"
        } else {
            ""
        }
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
