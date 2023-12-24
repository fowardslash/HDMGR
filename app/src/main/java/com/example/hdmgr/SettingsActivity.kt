package com.example.hdmgr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hdmgr.ui.theme.HDMGRTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HDMGRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Settings(goBack = {finish()})
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(goBack: () -> Unit = {}) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text(text = "Cài đặt") },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                scrollBehavior = scrollBehavior)
        }
    ) {
        pv -> Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(pv)) {
            Column(Modifier.padding(20.dp, 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Tình trạng mật khẩu: Chưa đặt")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Ổ khoá")

                    Column {
                        Text(text = "Đặt mật khẩu", fontSize = 20.sp)
                        Text(text = "Đặt mật khẩu để có thể truy cập vào các hoá đơn")
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    HDMGRTheme {
        Settings()
    }
}