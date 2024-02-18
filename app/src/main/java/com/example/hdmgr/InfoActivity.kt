package com.example.hdmgr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hdmgr.ui.theme.HDMGRTheme

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HDMGRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InfoView(onExit = {finish()})
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoView(onExit: () -> Unit = {}) {
    val context = LocalContext.current
    val res = stringResource(id = R.string.version)
    fun launchUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(i)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Thông tin ứng dụng") },
                navigationIcon = {
                    IconButton(onClick = { onExit() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                })
        },
    ) {
        pd -> Column(Modifier.padding(pd)) {
                Column(
                    Modifier
                        .padding(16.dp, 5.dp, 16.dp, 16.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Quản lý hoá đơn", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        Text(text = "Phiên bản $res", fontWeight = FontWeight.Bold)
                        Text(text = "Dự án này hoàn toàn mã nguồn mở, nghĩa là mã nguồn của nó được công khai và bất kỳ ai cũng có thể xem và chỉnh sửa.")
                        ElevatedButton(onClick = { launchUrl("https://github.com/fowardslash/HDMGR") }, Modifier.fillMaxWidth()) {
                            Text(text = "Github Repository")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Image(painter = painterResource(id = R.drawable.logo_author), contentDescription = "logo tác giả")
                        Text(text = "© 2023-2024 Tiêu Trí Quang. Dự án này được thiết kế và tạo ra bởi Quang. Sử dụng công nghệ Jetpack Compose và bộ Icon của Google, LLC. Icon của app lấy Icon của RemixIcon.")
                        ElevatedButton(onClick = { launchUrl("https://fowardslash.vercel.app") }, Modifier.fillMaxWidth()) {
                            Text(text = "Trang chủ")
                        }
                        ElevatedButton(onClick = { launchUrl("https://github.com/fowardslash") }, Modifier.fillMaxWidth()) {
                            Text(text = "Github")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    Row(
                        Modifier
                            .clickable { }
                            .fillMaxWidth()
                            .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "Thông tin")

                        Column {
                            Text(text = "Giấy phép nguồn mở", fontSize = 20.sp)
                        }
                    }
                    Divider()
                    Row(
                        Modifier
                            .clickable { }
                            .fillMaxWidth()
                            .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Changelog")

                        Column {
                            Text(text = "Nhật ký thay đổi", fontSize = 20.sp)
                        }
                    }
                    Divider()
                    Row(
                        Modifier
                            .clickable { }
                            .fillMaxWidth()
                            .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Outlined.Warning, contentDescription = "Issues")

                        Column {
                            Text(text = "Báo cáo lỗi / góp ý", fontSize = 20.sp)
                        }
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    HDMGRTheme {
        InfoView()
    }
}