package com.kkyy_96.controlmywindow.src.page

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.kkyy_96.controlmywindow.MainActivity
import com.kkyy_96.controlmywindow.src.config.MyControlConfig
import com.kkyy_96.controlmywindow.src.log.MyLog
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


object HomeDefault {

    @Composable
    fun EditButtonColor() = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))

    @Composable
    fun Spacer1(width: Int = 0, height: Int = 0) =
        Spacer(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    val isEditMod = remember { mutableStateOf(false) }
    val dataList = remember { mutableStateListOf(*ServerInfo.getServerList().toTypedArray()) }

    val isEditItem = remember { mutableStateOf(false) }
    val editItemInfo = remember { mutableStateOf(ServerItem()) }
    val scrollState = rememberLazyListState()
    val scrollIndex = remember { mutableStateOf(0) }
    LaunchedEffect(key1 = scrollIndex.value) {
        scrollState.animateScrollToItem(scrollIndex.value)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HomeHeader(
            isEditMod = isEditMod,
            dataList = dataList,
            scrollIndex = scrollIndex,
            editItemInfo = editItemInfo,
            isEditItem = isEditItem
        )
        HomeDefault.Spacer1(height = 5)
        HomeContent(
            isEditMod = isEditMod,
            dataList = dataList,
            scrollIndex = scrollIndex,
            editItemInfo = editItemInfo,
            isEditItem = isEditItem,
            scrollState = scrollState,
            navController = navController
        )
    }


}

@Composable
fun HomeHeader(
    isEditMod: MutableState<Boolean>,
    dataList: MutableList<ServerItem>,
    scrollIndex: MutableState<Int>,
    editItemInfo: MutableState<ServerItem>,
    isEditItem: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .background(Color(0xFFDADADA), RoundedCornerShape(5.dp))
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.End)
        ) {
            if (!isEditMod.value) {
                Button(
                    onClick = { isEditMod.value = true },
                    colors = HomeDefault.EditButtonColor(),
                    modifier = Modifier
                ) {
                    Text(text = "编辑")
                }
                Spacer(modifier = Modifier.width(10.dp))
            } else {
                Button(
                    onClick = {
                        editItemInfo.value = ServerItem()
                        isEditItem.value = true

                    },
                    colors = HomeDefault.EditButtonColor(),
                    modifier = Modifier
                ) {
                    Text(text = "添加")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        ServerInfo.saveServerList(dataList.toMutableList())
                        isEditMod.value = false
                    },
                    colors = HomeDefault.EditButtonColor(),
                    modifier = Modifier
                ) {
                    Text(text = "确认")
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

        }
    }


    if (isEditItem.value) {
        Dialog(onDismissRequest = { isEditItem.value = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), RoundedCornerShape(20.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isEditItem.value = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "X")
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    var lableModi = Modifier.width(40.dp)
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val input = remember { mutableStateOf(editItemInfo.value.name) }
                        Text(text = "名称", textAlign = TextAlign.End, modifier = lableModi)
                        HomeDefault.Spacer1(10, 0)
                        OutlinedTextField(
                            value = input.value,
                            onValueChange = {
                                input.value = it
                                editItemInfo.value.name = it
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    HomeDefault.Spacer1(0, 10)
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val input = remember { mutableStateOf(editItemInfo.value.ip) }
                        Text(text = "IP", textAlign = TextAlign.End, modifier = lableModi)
                        HomeDefault.Spacer1(10, 0)
                        OutlinedTextField(value = input.value, onValueChange = {
                            input.value = it
                            editItemInfo.value.ip = it
                        })
                    }
                    HomeDefault.Spacer1(0, 10)
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val input = remember { mutableStateOf(editItemInfo.value.port) }
                        Text(text = "PORT", textAlign = TextAlign.End, modifier = lableModi)
                        HomeDefault.Spacer1(10, 0)
                        OutlinedTextField(value = input.value, onValueChange = {
                            input.value = it
                            editItemInfo.value.port = it
                        })
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            isEditItem.value = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .padding(3.dp)
                    ) {
                        Text(text = "取消")
                    }
                    Button(
                        onClick = {
                            if (editItemInfo.value.id == 0L) {
                                editItemInfo.value.id = System.currentTimeMillis()
                                dataList.add(editItemInfo.value)
                                scrollIndex.value = dataList.size - 1
                            } else {
                                dataList.forEachIndexed { index, serverItem ->
                                    if (serverItem.id == editItemInfo.value.id) {
                                        dataList[index] = editItemInfo.value
                                    }
                                }
                            }
                            isEditItem.value = false
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                        modifier = Modifier
                            .padding(3.dp)
                    ) {
                        Text(text = "确认")
                    }
                }
            }

        }
    }

}

@Composable
fun HomeContent(
    isEditMod: MutableState<Boolean>,
    dataList: MutableList<ServerItem>,
    scrollIndex: MutableState<Int>,
    editItemInfo: MutableState<ServerItem>,
    isEditItem: MutableState<Boolean>,
    scrollState: LazyListState,

    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val delId = remember {
        mutableLongStateOf(0)
    }
    Column(
        modifier = modifier
    ) {

        LazyColumn(
            state = scrollState,
            modifier = Modifier
        ) {
            itemsIndexed(dataList, key = { _, it -> "${it.id}" }) { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = if (!isEditMod.value) Arrangement.Center else Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                            .background(Color(0xA67CC9C6), RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.99f)
                            .padding(2.dp)
                            .height(50.dp)
                    ) {
                        if (isEditMod.value) {
                            Row() {
                                Button(
                                    onClick = {
                                        var last = dataList[index - 1]
                                        dataList[index - 1] = item
                                        dataList[index] = last
                                        scrollIndex.value = index - 1
                                    },
                                    enabled = index != 0,
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = HomeDefault.EditButtonColor(),
                                    modifier = Modifier
                                        .width(40.dp)
                                        .padding(2.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(text = "↑")
                                }
                                Button(
                                    onClick = {
                                        var next = dataList[index + 1]
                                        dataList[index + 1] = item
                                        dataList[index] = next
                                        scrollIndex.value = index + 1
                                    },
                                    enabled = index != dataList.size - 1,
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = HomeDefault.EditButtonColor(),
                                    modifier = Modifier
                                        .width(40.dp)
                                        .padding(2.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(text = "↓")
                                }
                            }

                        }
                        ServerItemUi(
                            item,
                            onClick = {
                                val url = URLEncoder.encode(
                                    "http://${item.ip}:${item.port}?ts=${System.currentTimeMillis()}",
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(Router.WebView + "?url=$url")
                            },
                            modifier = Modifier
                                .weight(1f)

                        )
                        if (isEditMod.value) {
                            Row() {
                                Button(
                                    onClick = {
                                        editItemInfo.value = item
                                        isEditItem.value = true
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFF9800)),
                                    modifier = Modifier
                                        .width(40.dp)
                                        .padding(2.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(text = "✎")
                                }
                                Button(
                                    onClick = {
                                        delId.value = item.id
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFFF44336)),
                                    modifier = Modifier
                                        .width(40.dp)
                                        .padding(2.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(text = "✖︎")
                                }
                            }
                        }

                    }
                    HomeDefault.Spacer1(0, 5)
                }
            }
        }
    }

    if (delId.value > 0) {
        Dialog(onDismissRequest = { delId.value = 0 }) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), RoundedCornerShape(20.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier,
                ) {
                    Text(
                        text = "是否确认删除？",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                HomeDefault.Spacer1(height = 15)
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            delId.value = 0
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF169EDB)),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .padding(3.dp)
                    ) {
                        Text(text = "取消")
                    }
                    Button(
                        onClick = {
                            dataList.removeIf { it.id == delId.value }
                            delId.value = 0
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD30606)),
                        modifier = Modifier
                            .padding(3.dp)
                    ) {
                        Text(text = "确认")
                    }
                }
            }


        }
    }

}

