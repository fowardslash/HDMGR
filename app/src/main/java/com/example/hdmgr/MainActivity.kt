package com.example.hdmgr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hdmgr.db.DB
import com.example.hdmgr.model.Receipt
import com.example.hdmgr.ui.theme.HDMGRTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.Date

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Statistical(modifier: Modifier = Modifier){
    val context = LocalContext.current
    fun moveToSettings(){
        val i = Intent(context, SettingsActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text(text = "Thống kê tháng này") },
                navigationIcon = {
                    IconButton(onClick = { moveToSettings() }) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Cài đặt")
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),)
        }
    ) {
        pd -> Column(Modifier.padding(pd)) {
            Column(Modifier.padding(16.dp)) {


            }
    }
    }

}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppView(modifier: Modifier = Modifier){
    val item = listOf(
        Screens.MainScreen,
        Screens.StatisticalScreen
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    onClick = { navController.navigate("home") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }},
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home") }
                    , label = { Text(text = "Trang chủ")})
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == "stats" } == true,
                    onClick = { navController.navigate("stats") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }},
                    icon = { Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Stats") },
                    label = { Text(text = "Thống kê")})

            }
        }
    ) {
        ct -> NavHost(
        modifier = Modifier.padding(ct),
        navController = navController,
        startDestination = "home",
        enterTransition = { fadeIn(animationSpec = tween(200, easing = LinearEasing))},
        exitTransition = { fadeOut(animationSpec = tween(200, easing = LinearEasing)) }
        ){
            composable("home"){ MainView(name = "Android")}
            composable("stats"){ Statistical() }
        }
    }

}
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainView(name: String, modifier: Modifier = Modifier) {
    fun getIndex(col: String, cur: Cursor): Int{
        return cur.getColumnIndex(col)
    }
    var curRec by remember {
        mutableStateOf(Receipt())
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
    var sortingType by remember {
        mutableStateOf("date desc")
    }
    fun getReceipts(){
        val receiptC: Cursor = db.getReceipt(sortingType = sortingType)
        rList.clear()
        while (receiptC.moveToNext()){
            val date = receiptC.getString(5)
            val dateArr: List<String> = date.split("/")
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(dateArr[0].toInt(), dateArr[1].toInt(), dateArr[2].toInt())
            val rec = Receipt(receiptC.getString(getIndex("id", receiptC)), receiptC.getString(getIndex("content", receiptC)), receiptC.getInt(getIndex("price", receiptC)), receiptC.getString(getIndex("customer", receiptC)), receiptC.getString(getIndex("note", receiptC)), Date(calendar.timeInMillis))
            rList.add(rec)
        }
    }
    var isAdding by remember {
        mutableStateOf(false)
    }
    var isPickingDate by remember {
        mutableStateOf(false)
    }
    fun updateRec(r: Receipt){
        db.updateReceipt(r)
        getReceipts()
    }
    if(isAdding){
        InputDialog(onDismiss = { isAdding = false }, onConfirm = { r ->
            run {
                isAdding = if(r.getId() == ""){
                    db.addReceipt(r)
                    getReceipts()
                    false
                } else {
                    db.updateReceipt(r)
                    getReceipts()
                    false
                }

            }
        }, receipt = curRec)
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
        val i = Intent(context, SettingsActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
    var deleteId by remember {
        mutableIntStateOf(-1)
    }
    var isDelete by remember {
        mutableStateOf(false)
    }
    fun deleteRec(){
        db.deleteReceipt(deleteId)
        rList.removeAll {
            r -> r.getId() == deleteId.toString()
        }
    }
    var isMenuVisible by remember {
        mutableStateOf(false)
    }
    if(isDelete){
        AlertDialog(onDismissRequest = { isDelete = false }, confirmButton = { TextButton(onClick = { deleteRec(); isDelete = false }) {
            Text(text = "Có")
        } }, icon = { Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info"
        )}, text = { Text(text = "Bạn có muốn xoá hoá đơn này không?")}, dismissButton = {
            TextButton(onClick = { isDelete = false }) {
                Text(text = "Không")
            }
        })
    }
    var totalMoney by remember {
        mutableIntStateOf(0)
    }
    val totalMoneyCursor = db.getTotalMoney("")
    totalMoneyCursor.moveToNext()
    totalMoney = totalMoneyCursor.getInt(0)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
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
            FloatingActionButton(onClick = { isAdding = true; curRec = Receipt() }) {
                Icon(Icons.Filled.Add, "Thêm" )
            }
        }
    ) {
        innerPadding -> if(!rList.isEmpty()){
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ElevatedButton(onClick = { /*TODO*/ }, Modifier.weight(1.0f)) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Lọc")
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Tìm kiếm")
                        }
                        Button(onClick = { isMenuVisible = true }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_sort_24), contentDescription = "Lọc")
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Sắp xếp")
                            DropdownMenu(expanded = isMenuVisible, onDismissRequest = { isMenuVisible = false }) {
                                DropdownMenuItem(text = { Text(text = "Theo ngày (tăng dần)") }, onClick = { sortingType = "date"; getReceipts(); isMenuVisible = false }, leadingIcon = {
                                    if(sortingType == "date"){
                                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Bật")
                                    }
                                })
                                DropdownMenuItem(text = { Text(text = "Theo ngày (giảm dần)") }, onClick = { sortingType = "date desc"; getReceipts(); isMenuVisible = false }, leadingIcon = {
                                    if(sortingType == "date desc"){
                                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Bật")
                                    }
                                })
                                DropdownMenuItem(text = { Text(text = "Nội dung (A-Z)") }, onClick = {  sortingType = "content"; getReceipts(); isMenuVisible = false }, leadingIcon = {
                                    if(sortingType == "content"){
                                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Bật")
                                    }
                                })
                                DropdownMenuItem(text = { Text(text = "Nội dung (Z-A)") }, onClick = { sortingType = "content desc"; getReceipts(); isMenuVisible = false }, leadingIcon = {
                                    if(sortingType == "content desc"){
                                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Bật")
                                    }
                                })
                            }
                        }
                    }
                }
                LazyColumn(
                    Modifier
                        .padding(20.dp, 0.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ElevatedCard {
                                Column(
                                    Modifier
                                        .padding(20.dp)) {
                                    Text(text = "Tổng tiền")
                                    Text(text = "₫${DecimalFormat("#,###").format(totalMoney)}", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            ElevatedCard(Modifier.weight(1f)) {
                                Column(
                                    Modifier
                                        .padding(20.dp)) {
                                    Text(text = "Số hoá đơn")
                                    Text(text = rList.size.toString(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    items(items = rList){
                        RecCard(it, onDelete = {id ->
                        run {
                            isDelete = true
                            deleteId = id.toInt()
                        }
                    }, onUpdate = {rec -> run {
                        curRec = rec
                        isAdding = true
                    }})
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

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun RecCard(item: Receipt = Receipt(), modifier: Modifier = Modifier.fillMaxWidth(), onDelete: (id: String) -> Unit, onUpdate: (rec: Receipt) -> Unit){
    var isFinised by remember {
        mutableStateOf(item.isFinished)
    }
    var isMenuOpened by remember {
        mutableStateOf(false)
    }
    ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
        val formatter: DateFormat = DateFormat.getDateInstance();
        Column(Modifier
            .padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .weight(1.0f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(text = "#${item.getId()}", color = MaterialTheme.colorScheme.onBackground)
                    Text(text = item.getContent(), fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)

                }
                Row() {
                    Checkbox(checked = isFinised, onCheckedChange = { isFinised = !isFinised})
                    IconButton(onClick = { isMenuOpened = true }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Thêm")
                        DropdownMenu(expanded = isMenuOpened, onDismissRequest = { isMenuOpened = false }) {
                            DropdownMenuItem(text = { Text(text = "Sửa") }, onClick = { onUpdate(item); isMenuOpened = false })
                            DropdownMenuItem(text = { Text(text = "Xóa") }, onClick = { onDelete(item.getId()); isMenuOpened= false })
                        }
                    }
                }
        }
            ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    Text(text = item.getNote())
                }
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "")
                        Text(text = formatter.format(item.getDate()))
                    }
                }
                ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "")
                        Text(text = item.getCustomer())
                    }
                }
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${DecimalFormat("#,###").format(item.getMoney())} VND", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
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
    receipt: Receipt? = Receipt("init"),
){
    var content by remember {
        mutableStateOf(receipt?.getContent() ?: "")
    }
    var price by remember {
        mutableIntStateOf(receipt?.getMoney() ?: 0)
    }
    var note by remember {
        mutableStateOf(receipt?.getNote() ?: "")
    }
    var customer by remember {
        mutableStateOf(receipt?.getCustomer() ?: "")
    }
    var products = remember {
        mutableStateListOf<String>()
    }
    fun confirmAdd(){
        val rec = Receipt(if(receipt?.getId() == "init") "" else receipt?.getId() ?: "-1", content, price, customer, note, Date())
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
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Huỷ")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    TextButton(onClick = { confirmAdd() }) {
                        Text(text = if(receipt?.getId() == "") "Thêm" else "Sửa" )
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
        Statistical()
    }
}