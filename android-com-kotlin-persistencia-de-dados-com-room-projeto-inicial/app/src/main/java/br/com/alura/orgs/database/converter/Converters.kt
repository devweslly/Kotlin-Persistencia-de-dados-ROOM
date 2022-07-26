package br.com.alura.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    /*

    * Crie o conversor de BigDecimal para Double e vice-versa
    * para que o Room seja capaz de gerar um código compatível com o SQLite

    * Implemente os métodos de conversão:
        * recebe BigDecimal? e retorna Double?;
        * recebe Double? e retorna BigDecimal.
    * Adicione a anotação @TypeConverter para cada função.
    * Em seguida, no componente do Database, adicione a anotação
    * @TypeConverters e envie como argumento a referência da classe de conversão.

    */

    @TypeConverter
    fun deDouble(valor: Double?): BigDecimal {
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?): Double? {
        return valor?.let { valor.toDouble() }
    }
}