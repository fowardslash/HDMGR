package com.example.hdmgr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class Screens(val Route: String, val name: String){
    object MainScreen : Screens("home", "Trang chủ")
    object StatisticalScreen : Screens("stats", "Thống kê")
}