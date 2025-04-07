package com.m7.users.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.m7.users.R
import com.m7.users.core.isName
import com.m7.users.core.isTitle
import com.m7.users.data.model.User
import com.m7.users.ui.theme.UsersTheme

@Preview
@Composable
fun AddUserScreenPreview() {
    UsersTheme {
        AddUserDialog(
            {},
            {},
        )
    }
}


@Composable
fun AddUserDialog(
    onDismiss: () -> Unit,
    onConfirmation: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var jobTitle by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf(User.Gender.Male) }

    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var jobTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    var ageError by rememberSaveable { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(modifier = modifier.fillMaxWidth()) {

            // dialog title
            Text(
                text = stringResource(id = R.string.add_user),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(20.dp)
            )

            // name
            TextField(
                text = name,
                onTextChanged = {
                    name = it
                    nameError = if (it.isName())
                        context.getString(R.string.enter_valid_name)
                    else null
                },
                stringResource(R.string.name),
                error = nameError
            )

            // job title
            TextField(
                text = jobTitle,
                onTextChanged = {
                    jobTitle = it
                    jobTitleError = if (it.isTitle())
                        context.getString(R.string.enter_valid_job_title)
                    else null
                },
                stringResource(R.string.job_title),
                error = jobTitleError
            )

            // age
            TextField(
                text = age,
                onTextChanged = {
                    ageError = null
                    age = it
                },
                stringResource(R.string.age),
                suffix = stringResource(R.string.year),
                error = ageError,
                keyboardType = KeyboardType.Number
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // gender label
                Text(
                    text = stringResource(R.string.gender) + " :",
                    fontWeight = FontWeight.Light
                )

                // gender
                LazyRow {
                    User.Gender.entries.also { genders ->
                        items(genders.count(), key = { genders[it].ordinal }) {
                            FilterChip(
                                onClick = {
                                    gender = genders[it]
                                },
                                selected = gender == genders[it],
                                label = { Text(text = genders[it].name) },
                                leadingIcon = {
                                    if (gender == genders[it]) {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Done icon",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Confirm
            Button(
                onClick = {
                    // validate inputs
                    var shouldReturn = false

                    if (name.isName()) {
                        nameError = context.getString(R.string.enter_valid_name)
                        shouldReturn = true
                    }

                    if (jobTitle.isTitle()) {
                        jobTitleError = context.getString(R.string.enter_valid_job_title)
                        shouldReturn = true
                    }

                    val ageAsInt = try {
                        age.toInt()
                    } catch (e: NumberFormatException) {
                        ageError = context.getString(R.string.enter_valid_age)
                        e.printStackTrace()
                        shouldReturn = true
                        0
                    }

                    if (shouldReturn) {
                        return@Button
                    }

                    // proceed after validating inputs
                    onConfirmation(
                        User(
                            name,
                            jobTitle,
                            ageAsInt,
                            gender,
                            0
                        )
                    )
                    onDismiss()
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp)
            ) {
                Text(stringResource(R.string.add))
            }

            // Cancel
            TextButton(
                onClick = { onDismiss() },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = stringResource(R.string.cancel), color = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    UsersTheme {
        TextField(
            text = "",
            onTextChanged = {},
            "place holder",
        )
    }
}

@Composable
fun TextField(
    text: String,
    onTextChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    suffix: String? = null,
    error: String? = null
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChanged(it) },
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        suffix = { suffix?.also { Text(text = it) } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        supportingText = error?.let { { Text(text = it, color = Color.Red) } },
        isError = error != null,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 15.dp),
    )
}

@Preview
@Composable
fun AddUserButtonExpandedPreview() {
    UsersTheme {
        AddUserButton(true, {})
    }
}

@Preview
@Composable
fun AddUserButtonCollapsedPreview() {
    UsersTheme {
        AddUserButton(false, {})
    }
}

@Composable
fun AddUserButton(expand: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val addUser_res = stringResource(R.string.add_user)
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = { Icon(Icons.Outlined.Add, addUser_res) },
        text = { Text(text = addUser_res) },
        expanded = expand,
    )
}