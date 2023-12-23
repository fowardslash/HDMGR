package com.example.hdmgr

import android.app.DatePickerDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hdmgr.model.Receipt
import com.example.hdmgr.ui.theme.HDMGRTheme
import kotlin.math.log
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                return
            }
        })
        val r: Receipt = Receipt()
        setContent {
            HDMGRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppView()
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppView(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home",
        enterTransition = { fadeIn(animationSpec = tween(200, easing = LinearEasing))},
        exitTransition = { fadeOut(animationSpec = tween(200, easing = LinearEasing)) }
    ){
        composable("Home"){ MainView(name = "Android", onNavigateNext = {navController.navigate("Second")})}
        composable("Second"){ backStackEntry -> SecondView(modifier = Modifier.background(MaterialTheme.colorScheme.background))}
    }
}
@Composable
fun SecondView(modifier: Modifier = Modifier){
    Column {
        Text(text = "Second View!!")
    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainView(name: String, modifier: Modifier = Modifier, onNavigateNext: () -> Unit) {
    val filterText by remember { mutableStateOf("Hôm nay")}
    var list: List<String> by remember { mutableStateOf(emptyList()) }
    var isPickingDate by remember {
        mutableStateOf(false)
    }

    fun addRandom(){
        val tmpList: ArrayList<String> = ArrayList();
        list.toCollection(tmpList)
        tmpList.add(Random.nextInt(10).toString())
        list = tmpList
    }
    fun deleteRandom(){
        val tmpList: ArrayList<String> = ArrayList();
        list.toCollection(tmpList)
        tmpList.removeAt((list.indices).random())
        list = tmpList
    }
    fun showDatePicker(){
        isPickingDate = true
    }
    if(isPickingDate){
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(onDismissRequest = { isPickingDate = false },
            confirmButton = {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Chọn ngày")
                }
            }, modifier = Modifier.padding(10.dp)) {
            DatePicker(state = datePickerState)
        }
    }
    var filterList = remember {
        mutableStateListOf<Boolean>().apply { addAll(listOf(false, false, false)) }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(filterText, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                      IconButton(onClick = { showDatePicker() }) {
                          Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Lịch")
                      }

                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "Đăng xuất")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {

        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Thêm" )
            }
        }
    ) {
        innerPadding -> Column(Modifier.padding(innerPadding)) {
            Column(Modifier.padding(20.dp, 0.dp)) {
                ChipArray(isFiltered = false, onFilter = {res ->
                    run {
                        for(i in filterList.indices){
                            if(filterList[i] != res[i]){
                                filterList[i] = res[i]
                            }
                        }
                        /*
                            TODO: Implement lọc theo tháng năm ngày.
                         */
                    }
                })
                Text(text = filterList.toList().joinToString())
                recCard()
            }

        }



    }
}

@Composable
fun recCard(item: Receipt = Receipt(), modifier: Modifier = Modifier.fillMaxWidth()){
    Card(modifier) {
        Column(Modifier.padding(10.dp)) {
            Text(text = "Hi", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Date • Customer")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Thành tiền")
                Text(text = "200.000 VND", fontSize = 20.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipArray(onFilter: (result: SnapshotStateList<Boolean>) -> Unit, modifier: Modifier = Modifier, isFiltered: Boolean){
    val filterText = remember { mutableStateListOf<Boolean>().apply { addAll(listOf(true, false, false)) } }
    fun changeFilter(value: Boolean, index: Int){
        filterText[index] = value
        Log.d("d", filterText[index].toString())
        onFilter(filterText)
    }
    Column {
        Text(text = "Lọc theo")
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            FilterChip(selected = filterText[0], onClick = { changeFilter(!filterText[0], 0) }, label = { Text(text = "Ngày") })
            Spacer(modifier = Modifier.width(10.dp))
            FilterChip(selected = filterText[1], onClick = { changeFilter(!filterText[1], 1) }, label = { Text(text = "Tháng") })
            Spacer(modifier = Modifier.width(10.dp))
            FilterChip(selected = filterText[2], onClick = { changeFilter(!filterText[2], 2) }, label = { Text(text = "Năm") })
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HDMGRTheme {
        AppView()
    }
}