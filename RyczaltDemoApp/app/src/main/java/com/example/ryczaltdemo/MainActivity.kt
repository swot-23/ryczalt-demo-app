
package com.example.ryczaltdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Income(val amount: Double)
data class FixedAsset(val name: String, val value: Double)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    var incomes by remember { mutableStateOf(listOf<Income>()) }
    var rate by remember { mutableStateOf(0.12) }
    var vatActive by remember { mutableStateOf(true) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Ryczałt Demo – MVP") })
    }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {

            Text("Stawka ryczałtu: ${(rate * 100).toInt()}%")
            Row {
                listOf(0.02,0.03,0.055,0.085,0.12,0.15,0.17).forEach {
                    Button(onClick = { rate = it }, modifier = Modifier.padding(4.dp)) {
                        Text("${(it*100).toInt()}%")
                    }
                }
            }

            Row {
                Text("VAT czynny")
                Switch(checked = vatActive, onCheckedChange = { vatActive = it })
            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = { incomes = incomes + Income(1000.0) }) {
                Text("Dodaj przykładowy przychód 1000 zł")
            }

            Spacer(Modifier.height(12.dp))

            val revenue = incomes.sumOf { it.amount }
            val tax = revenue * rate

            Text("Przychód: $revenue zł")
            Text("Podatek ryczałt: $tax zł")
            if (vatActive) Text("VAT (23% demo): ${revenue * 0.23} zł")

            Spacer(Modifier.height(16.dp))
            Text("Ewidencja przychodów:")

            LazyColumn {
                items(incomes) {
                    Text("${it.amount} zł")
                }
            }
        }
    }
}
