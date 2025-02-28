package com.amonteiro.a2025_02_supvinci_parisb.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amonteiro.a2025_02_supvinci_parisb.ui.theme._2025_02_supvinci_parisbTheme

@Preview( )
@Preview( uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyErrorPreview() {
    _2025_02_supvinci_parisbTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                //Je mets 2 versions pour tester avec et sans message d'erreur
                MyError(errorMessage = "Avec message d'erreur")
                Text("Sans erreur : ")
                MyError(errorMessage = "")
                Text("----------")
            }
        }
    }
}

@Composable
fun MyError(modifier: Modifier = Modifier, errorMessage:String?) {
    AnimatedVisibility(modifier= modifier,  visible = !errorMessage.isNullOrBlank()) {
        Text(
            text = errorMessage ?: "",
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onErrorContainer).padding(4.dp),
            color = MaterialTheme.colorScheme.errorContainer
        )
    }
}