package com.example.hdmgr

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hdmgr.ui.theme.HDMGRTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HDMGRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginView("Android", onLogin = {mail, pass -> login(mail, pass)})
                }
            }
        }
    }
    fun login(mail: String, passowrd: String){
        val i: Intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(name: String, modifier: Modifier = Modifier, onLogin: (String, String) -> Unit = {a, b -> {}}) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    fun login(){

    }
    Scaffold {
        innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Chào mừng, vui lòng đăng nhập", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = username, label = { Text(text = "Email")}, onValueChange = { it -> username = it})
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = password, label = { Text(text = "Mật khẩu")}, onValueChange = { it -> password = it}, visualTransformation = PasswordVisualTransformation())
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { onLogin(username, password) }) {
                Text(text = "Đăng nhập")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    HDMGRTheme {
        LoginView("Android")
    }
}