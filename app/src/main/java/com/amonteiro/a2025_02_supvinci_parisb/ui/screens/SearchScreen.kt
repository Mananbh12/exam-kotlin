package com.amonteiro.a2025_02_supvinci_parisb.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.amonteiro.a2025_02_supvinci_parisb.R
import com.amonteiro.a2025_02_supvinci_parisb.model.PictureBean
import com.amonteiro.a2025_02_supvinci_parisb.ui.MyError
import com.amonteiro.a2025_02_supvinci_parisb.ui.theme._2025_02_supvinci_parisbTheme
import com.amonteiro.a2025_02_supvinci_parisb.viewmodel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
    navHostController: NavHostController? = null
) {
    var isDarkTheme by remember { mutableStateOf(false) }

    _2025_02_supvinci_parisbTheme(darkTheme = isDarkTheme) {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list by mainViewModel.dataList.collectAsStateWithLifecycle()
                val runInProgress by mainViewModel.runInProgress.collectAsStateWithLifecycle()
                val errorMessage by mainViewModel.errorMessage.collectAsStateWithLifecycle()

                var searchText = remember { mutableStateOf("") }

                SearchBar(searchText = searchText)

                MyError(errorMessage = errorMessage)

                AnimatedVisibility(visible = runInProgress) {
                    CircularProgressIndicator()
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(5f)
                ) {
                    items(list.size) {
                        PictureRowItem(data = list[it], navHostController = navHostController)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { searchText.value = "" },
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Clear")
                    }
                    Button(
                        onClick = { mainViewModel.loadMovies(searchText.value) },
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Search")
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Search")
                    }
                    IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>) {
    TextField(
        value = searchText.value,
        onValueChange = { newValue: String -> searchText.value = newValue },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { Text("Enter movie title") },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(
    modifier: Modifier = Modifier,
    data: PictureBean,
    navHostController: NavHostController?
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        GlideImage(
            model = data.url,
            contentDescription = data.title,
            modifier = Modifier
                .size(88.dp)
                .padding(4.dp),
            loading = placeholder(R.drawable.image),
            failure = placeholder(R.drawable.image)
        )

        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = data.title, style = MaterialTheme.typography.titleLarge)
            val text = if (expanded) data.longText else (data.longText.take(20) + "...")
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    val viewModel = viewModel<MainViewModel>()
    viewModel.loadFakeData(runInProgress = true, errorMessage = "Une erreur")
    SearchScreen(mainViewModel = viewModel)
}