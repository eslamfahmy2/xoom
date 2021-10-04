package com.chuify.xoomclient.presentation.ui.vendors.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.chuify.xoomclient.R


@Composable
fun VendorScreen(
    phone: String,
    onLogin: () -> Unit,
    onValueChanged: (String) -> Unit,
) {

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        val (header, content) = createRefs()

        Image(
            painterResource(R.drawable.xoom_gas_rider),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(content.top)
                }
        )

        Card(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            elevation = 20.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp),
                    text = stringResource(R.string.get_your_gas_delivered),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp),
                    text = stringResource(R.string.log_in_or_sign_up_to_place_your_order),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(5.dp),
                                color = Color.Gray
                            )
                            .padding(10.dp)
                            .width(40.dp)
                            .height(35.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(
                            id = R.drawable.flag_kenya
                        ), contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    TextField(
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(5.dp),
                                color = Color.Gray
                            )
                            .align(Alignment.CenterVertically),
                        value = phone,
                        onValueChange = { onValueChanged(it) },
                        leadingIcon = {
                            Text(
                                text = "+254",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 16.sp)
                        },
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onLogin() }
                        ),
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.secondaryVariant,
                            fontSize = 16.sp
                        )

                    )
                }

                Spacer(modifier = Modifier.padding(4.dp))

                Button(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    onClick = { onLogin() })
                {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                shape = MaterialTheme.shapes.large,
                                color = MaterialTheme.colors.primary
                            ),
                        text = stringResource(R.string.con),
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }

            }
        }


    }

}