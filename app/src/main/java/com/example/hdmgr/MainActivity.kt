package com.example.hdmgr

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hdmgr.db.DB
import com.example.hdmgr.model.Receipt
import com.example.hdmgr.ui.theme.HDMGRTheme
import java.text.DateFormat
import java.util.Date
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
    fun getIndex(col: String, cur: Cursor): Int{
        return cur.getColumnIndex(col)
    }
    val db = DB(LocalContext.current)
    val receiptC: Cursor = db.getReceipt()
    val filterText by remember { mutableStateOf("Tất cả")}
    val rList = remember { mutableStateListOf<Receipt>() }
    if(rList.isEmpty()){
        while (receiptC.moveToNext()){
            val date = receiptC.getString(5)
            val dateArr: List<String> = date.split("/")
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(dateArr[0].toInt(), dateArr[1].toInt(), dateArr[2].toInt())
            val rec = Receipt(receiptC.getString(getIndex("id", receiptC)), receiptC.getString(getIndex("content", receiptC)), receiptC.getInt(getIndex("price", receiptC)), receiptC.getString(getIndex("customer", receiptC)), receiptC.getString(getIndex("note", receiptC)), Date(calendar.timeInMillis))
            rList.add(rec)
        }
    }

    fun getReceipts(){
        val receiptC: Cursor = db.getReceipt()
        rList.clear()
        while (receiptC.moveToNext()){
            val date = receiptC.getString(5)
            val dateArr: List<String> = date.split("/")
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(dateArr[0].toInt(), dateArr[1].toInt(), dateArr[2].toInt())
            val rec = Receipt(receiptC.getString(getIndex("id", receiptC)), receiptC.getString(getIndex("id", receiptC)), receiptC.getInt(getIndex("price", receiptC)), receiptC.getString(getIndex("customer", receiptC)), receiptC.getString(getIndex("note", receiptC)), Date(calendar.timeInMillis))
            rList.add(rec)
        }
    }
    var isAdding by remember {
        mutableStateOf(false)
    }
    var isPickingDate by remember {
        mutableStateOf(false)
    }
    if(isAdding){
        InputDialog(onDismiss = { isAdding = false }, onConfirm = {r ->
            run {
                db.addReceipt(r)
                getReceipts()
                isAdding = false
            }
        })
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
    val filterList = remember {
        mutableStateListOf<Boolean>().apply { addAll(listOf(false, false, false)) }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val context: Context = LocalContext.current
    fun moveToSettings(){
        val i:Intent = Intent(context, SettingsActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
    var deleteId by remember {
        mutableIntStateOf(-1)
    }
    var isDelete by remember {
        mutableStateOf(false)
    }
    if(isDelete){
        AlertDialog(onDismissRequest = { isDelete = false }, confirmButton = { /*TODO*/ }, icon = { Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info"
        )}, text = { Text(text = "Bạn có muốn xoá hoá đơn có mã là '$deleteId' này không?")})
    }

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
                    IconButton(onClick = { showDatePicker() }) {
                        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Lịch")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { moveToSettings() }) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Cài đặt")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {

        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAdding = true }) {
                Icon(Icons.Filled.Add, "Thêm" )
            }
        }
    ) {
        innerPadding -> if(!rList.isEmpty()){
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()) {
                LazyColumn(
                    Modifier
                        .padding(20.dp, 0.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items = rList){
                        RecCard(it, onDelete = {id ->
                        run {
                            isDelete = true
                            deleteId = id.toInt()
                        }
                    })
                    }
                }

            }
    } else Column(
        Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = Icons.Outlined.Info, contentDescription = "Not found", Modifier.size(58.dp))
        Text(text = "Không có hoá đơn nào", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Vui lòng thêm một hoá đơn")
    }



    }
}

@Composable
fun RecCard(item: Receipt = Receipt(), modifier: Modifier = Modifier.fillMaxWidth(), onDelete: (id: String) -> Unit){
    Card(modifier) {
        val formatter: DateFormat = DateFormat.getDateInstance();
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(10.dp)) {
                Text(text = item.getContent(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = item.getNote())
                Text(text = "${formatter.format(item.getDate())} • ${item.getCustomer()}")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${item.getMoney()} VND", fontSize = 20.sp)
                }
            }
            Row {
                IconButton(onClick = { onDelete(item.getId()) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Xoá")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Xoá")
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipArray(onFilter: (result: SnapshotStateList<Boolean>) -> Unit, modifier: Modifier = Modifier, isFiltered: Boolean){
    val filterText = remember { mutableStateListOf<Boolean>().apply { addAll(listOf(true, false, false)) } }
    fun changeFilter(value: Boolean, index: Int){
        for(i in filterText.indices){
            filterText[i] = i == index
        }
        Log.d("d", filterText[index].toString())
        onFilter(filterText)
    }
    Column {
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            FilterChip(selected = filterText[0], onClick = { changeFilter(!filterText[0], 0) }, label = { Text(text = "Ngày") }, enabled = isFiltered)
            Spacer(modifier = Modifier.width(10.dp))
            FilterChip(selected = filterText[1], onClick = { changeFilter(!filterText[1], 1) }, label = { Text(text = "Tháng") }, enabled = isFiltered)
            Spacer(modifier = Modifier.width(10.dp))
            FilterChip(selected = filterText[2], onClick = { changeFilter(!filterText[2], 2) }, label = { Text(text = "Năm") }, enabled = isFiltered)
        }
    }
}
@Composable
fun InputDialog(
    onDismiss: () -> Unit,
    onConfirm: (Receipt) -> Unit,
){
    var content by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableIntStateOf(0)
    }
    var note by remember {
        mutableStateOf("")
    }
    var customer by remember {
        mutableStateOf("")
    }
    fun confirmAdd(){
        val rec = Receipt("id", content, price, customer, note, Date())
        onConfirm(rec)
    }
    Dialog(onDismissRequest = {onDismiss()}) {

        Card(modifier = Modifier
            .fillMaxWidth()
            ){
            Column(Modifier.padding(16.dp)) {
                Text(text = "Nhập nội dung hoá đơn", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = content, onValueChange = {content = it}, label = { Text(
                    text = "Nội dung"
                )})
                Row {
                    OutlinedTextField(modifier = Modifier.weight(1.0f, false) ,value = if(price == 0) {""} else {price.toString()}, onValueChange = {price = it.toInt()}, label = { Text(
                        text = "Đơn giá"
                    )}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(modifier = Modifier.weight(1.0f, false),value = customer, onValueChange = {customer = it}, label = { Text(
                        text = "Khách hàng"
                    )})
                }
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = note, onValueChange = {note = it}, label = { Text(
                    text = "Ghi chú"
                )})
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = { confirmAdd() }) {
                        Text(text = "Thêm")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Huỷ")
                    }
                }

            }
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