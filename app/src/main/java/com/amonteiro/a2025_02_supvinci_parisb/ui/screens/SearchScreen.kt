package com.amonteiro.a2025_02_supvinci_parisb.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amonteiro.a2025_02_supvinci_parisb.ui.theme._2025_02_supvinci_parisbTheme
import com.amonteiro.a2025_02_supvinci_parisb.viewmodel.MainViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = MainViewModel()) {
    Column(modifier= modifier.background(Color.LightGray).fillMaxSize()) {
        println("SearchScreen()")
        Text(text = "Text1",fontSize = 20.sp, modifier = Modifier.background(Color.Yellow).padding(4.dp).background(Color.Red))
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2",fontSize = 14.sp)


        //Observation
        val list = mainViewModel.dataList.collectAsStateWithLifecycle().value

        list.forEach {
            PictureRowItem(it.title, Color.Blue)
        }
    }
}

@Composable
fun PictureRowItem(text:String, color:Color){
    Text(
        text = text,
        fontSize = 14.sp,
        color = color)
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _2025_02_supvinci_parisbTheme {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val viewmodel = MainViewModel()
            viewmodel.loadFakeData(true, "une erreur")
            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = viewmodel)
        }
    }
}