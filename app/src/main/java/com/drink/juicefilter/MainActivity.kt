package com.drink.juicefilter

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drink.thirsty.ui.theme.ThirstyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ThirstyTheme {
                SetView()
            }
        }

        if(!isServiceRunning(this)){
            Toast.makeText(this, "Enable Thirsty service", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }
}

@Composable
fun SetView(){
    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        Column(Modifier.padding(all = 40.dp)) {
            ThirstyImage(Modifier,R.drawable.thirsty)
            Greeting(
                message = "This app checks if your phone's juice is not spiked",
                modifier = Modifier.padding(top = 40.dp)
            )
        }

    }
}

@Composable
fun ThirstyImage(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    contentDescription: String? = null
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier
    )
}
fun isServiceRunning(context: Context): Boolean{
    val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)

    for (enabledService in enabledServices) {
        val enabledServiceInfo = enabledService.resolveInfo.serviceInfo
        if (enabledServiceInfo.packageName == context.packageName && enabledServiceInfo.name == JuiceFilterService::class.java.name) return true
    }

    return false
}

@Composable
fun Greeting(message: String, modifier: Modifier = Modifier) {
    Text(
        text = "$message!",
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThirstyTheme {
        SetView()
    }
}