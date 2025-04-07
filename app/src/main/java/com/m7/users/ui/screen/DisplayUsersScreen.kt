package com.m7.users.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m7.users.R
import com.m7.users.data.model.User
import com.m7.users.ui.theme.UsersTheme

@Preview(showBackground = true)
@Composable
fun DisplayUsersScreenPreview() {
    UsersTheme {
        UsersList(
            listOf(
                User(
                    "user 1",
                    "eng",
                    25,
                    User.Gender.Male,
                    1
                ),
                User(
                    "user 2",
                    "doc",
                    28,
                    User.Gender.Female,
                    2
                ),
                User(
                    "user 3",
                    "acc",
                    30,
                    User.Gender.Male,
                    3
                ),
            ),
        )
    }
}

@Composable
fun UsersList(
    users: List<User>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        state = listState
    ) {
        items(count = users.count(), key = { users[it].id }) {
            UserItem(users[it])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UsersTheme {
        UserItem(
            User(
                "user 1",
                "eng",
                25,
                User.Gender.Male,
                1
            )
        )
    }
}

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier
) {
    val contentsModifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)

    Box(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
        ) {
            // name
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = contentsModifier
            )

            // job title
            Text(text = user.jobTitle, contentsModifier)

            Column {
                HorizontalLine()
                Row(
                    modifier = contentsModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // age label
                    Text(
                        text = stringResource(id = R.string.age) + " :",
                        fontWeight = FontWeight.Thin
                    )

                    // age
                    Text(
                        text = user.age.toString() + " " + stringResource(id = R.string.year),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 15.dp)
                    )
                }

                HorizontalLine()
                Row(
                    modifier = contentsModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // gender label
                    Text(
                        text = stringResource(id = R.string.gender) + " :",
                        fontWeight = FontWeight.Thin
                    )

                    // gender
                    Text(
                        text = user.gender.name,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalLine(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.tertiary)
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyUsersListPreview() {
    UsersTheme {
        EmptyUsersList()
    }
}

@Composable
fun EmptyUsersList(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier.align(Alignment.Center)) {
            // title
            Text(
                text = stringResource(R.string.empty_list_title),
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // hint
            Text(
                text = stringResource(R.string.empty_list_hint),
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}