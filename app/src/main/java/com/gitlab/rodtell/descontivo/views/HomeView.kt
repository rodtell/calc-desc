package com.gitlab.rodtell.descontivo.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gitlab.rodtell.descontivo.R
import com.gitlab.rodtell.descontivo.components.Alert
import com.gitlab.rodtell.descontivo.components.MainButton
import com.gitlab.rodtell.descontivo.components.MainTextField
import com.gitlab.rodtell.descontivo.components.SpaceH
import com.gitlab.rodtell.descontivo.components.TwoCards


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.descontivo), color = Color.White) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) {
        ContentHomeView(it)
    }
}

@Composable
fun ContentHomeView(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var price by remember { mutableStateOf("") }
        var discount by remember { mutableStateOf("") }
        var priceDiscount by remember { mutableDoubleStateOf(0.00) }
        var totalDiscount by remember { mutableDoubleStateOf(0.00) }
        var showAlert by remember { mutableStateOf(false) }

        TwoCards(
            title1 = "Total",
            number1 = totalDiscount,
            title2 = stringResource(R.string.desconto_two_cards),
            number2 = priceDiscount,
        )

        MainTextField(
            value = price,
            onValueChange = { price = it },
            label = stringResource(R.string.preco)
        )
        SpaceH()
        MainTextField(
            value = discount,
            onValueChange = { discount = it },
            label = stringResource(R.string.desconto)
        )
        SpaceH(10.dp)
        MainButton(text = stringResource(R.string.calcular)) {
            if (price != "" && discount != "") {
                priceDiscount = calculatePrice(price.toDouble(), discount.toDouble())
                totalDiscount = calculateDiscount(price.toDouble(), discount.toDouble())
            } else {
                showAlert = true
            }
        }
        SpaceH()
        MainButton(text = stringResource(R.string.limpar), color = Color.Red) {
            price = ""
            discount = ""
            priceDiscount = 0.0
            totalDiscount = 0.0
        }
        if (showAlert) {
            Alert(
                title = stringResource(R.string.atencao_alerta),
                message = stringResource(R.string.digite_valor_e_desconto),
                confirmText = stringResource(R.string.aceitar_alerta),
                onConfirmClick = { showAlert = false }) {

            }
        }
    }
}

fun calculatePrice(price: Double, discount: Double): Double {
    val result = price - calculateDiscount(price, discount)
    return kotlin.math.round(result * 100) / 100.0
}

fun calculateDiscount(price: Double, discount: Double): Double {
    val result = price * (1 - discount / 100)
    return kotlin.math.round(result * 100) / 100.0
}