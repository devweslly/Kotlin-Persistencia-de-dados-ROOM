package br.com.alura.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity // sempre necessário, independente da Entidade
@Parcelize
data class Produto(
        // A notação @PrimaryKey (chave-primária) sempre necessária, determina um campo
        // ou coluna que terá um valor único para cada registro, neste caso,
        // valor único para a propetie id.
        // O id sempre precisa passar o valor 0, para o Room entender que ele vai gerar este valor.
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null
) : Parcelable
