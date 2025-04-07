package com.m7.users.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m7.users.ui.screen.AddUserButton
import com.m7.users.ui.screen.AddUserDialog
import com.m7.users.ui.screen.DisplayUsersScreenPreview
import com.m7.users.ui.screen.EmptyUsersList
import com.m7.users.ui.screen.UsersList
import com.m7.users.ui.theme.UsersTheme
import dagger.hilt.android.AndroidEntryPoint

@Preview(showSystemUi = true)
@Composable
fun MainPreview() {
    UsersTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                AddUserButton(
                    true,
                    onClick = {}
                )
            }
        ) { innerPadding ->
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    DisplayUsersScreenPreview()
                }
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UsersTheme {
                val mainViewModel = viewModel<MainViewModel>()
                val medicines by mainViewModel.users.collectAsState()

                val listState = rememberLazyListState()
                val expandFab by remember { derivedStateOf { listState.lastScrolledBackward || !listState.canScrollBackward } }

                var showAddDialog by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        AddUserButton(
                            expandFab,
                            onClick = {
                                showAddDialog = true
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (medicines.isNotEmpty()) {
                            UsersList(
                                medicines,
                                listState = listState
                            )
                        } else {
                            EmptyUsersList()
                        }

                        if (showAddDialog)
                            AddUserDialog(
                                onDismiss = { showAddDialog = false },
                                onConfirmation = {
                                    mainViewModel.addUser(it)
                                }
                            )
                    }
                }
            }
        }
    }
}