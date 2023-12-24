package com.example.hdmgr

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.hdmgr.model.UserPrefs
import com.example.hdmgr.ui.theme.HDMGRTheme
import com.example.hdmgr.ui.theme.ThemeState
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

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
fun Settings(goBack: () -> Unit) {
    var isChangeApp by remember {
        mutableStateOf(false)
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    if(isChangeApp){
        AppearanceDialog(onDismiss = {  isChangeApp = false  })
    }

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

            Column(Modifier.padding(0.dp, 5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(
                    Modifier
                        .clickable { isChangeApp = true }
                        .fillMaxWidth()
                        .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.outline_color_lens_24), contentDescription = "Sửa")

                    Column {
                        Text(text = "Giao diện", fontSize = 20.sp)
                        Text(text = "Theo hệ thống")
                    }
                }
                Divider()
                Text(modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp) ,text = "Tình trạng mật khẩu: Chưa đặt")
                Row(
                    Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Ổ khoá")

                    Column {
                        Text(text = "Mật khẩu", fontSize = 20.sp)
                        Text(text = "Mật khẩu truy cập vào các hoá đơn")
                    }
                }
                Divider()
                Row(
                    Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .padding(20.dp) ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "Thông tin")

                    Column {
                        Text(text = "Thông tin ứng dụng", fontSize = 20.sp)
                    }
                }
            }

        }
    }
}
@Composable
fun AppearanceDialog(onDismiss: () -> Unit){
    val context = LocalContext.current
    var darkMode by remember {
        mutableIntStateOf(0)
    }
    val pref = UserPrefs(context)
    val st by pref.getDarkModeState().collectAsState(0)
    darkMode = st
    suspend fun setDarkMode(value: Int){
        pref.writeToDarkmodePref(value)
    }
    val coroutineScore = rememberCoroutineScope()
    Dialog(onDismissRequest = {onDismiss()}) {
        Card(
            Modifier
                .fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = "Giao diện", fontSize = 20.sp)
                Column(Modifier.selectableGroup()) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = darkMode == 1, onClick = { ThemeState.isLight = 1;
                            coroutineScore.launch {
                                setDarkMode(1)
                            }
                            onDismiss()
                        })
                        Text(text = "Sáng")
                    }
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = darkMode == 2, onClick = { ThemeState.isLight = 2
                            coroutineScore.launch {
                                setDarkMode(2)
                            }
                            onDismiss()
                        })
                        Text(text = "Tối")
                    }
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = darkMode == 0, onClick = { ThemeState.isLight = 0
                            coroutineScore.launch {
                                setDarkMode(0)
                            }
                            onDismiss()
                        })
                        Text(text = "Hệ thống")
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
        Settings({})
    }
}