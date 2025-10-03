package app.futured.kmptemplate.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.android.ui.navigation.RootNavHostUi
import app.futured.kmptemplate.android.ui.theme.MyApplicationTheme
import app.futured.kmptemplate.feature.navigation.root.RootNavHost
import app.futured.kmptemplate.feature.navigation.root.RootNavHostFactory
import app.futured.kmptemplate.feature.ui.base.DefaultAppComponentContext
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {

    private lateinit var rootNavHost: RootNavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootNavHost = retainedComponent { retainedContext ->
            RootNavHostFactory.create(DefaultAppComponentContext(retainedContext))
        }
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RootNavHostUi(navHost = rootNavHost)
                }
            }
        }
    }
}
