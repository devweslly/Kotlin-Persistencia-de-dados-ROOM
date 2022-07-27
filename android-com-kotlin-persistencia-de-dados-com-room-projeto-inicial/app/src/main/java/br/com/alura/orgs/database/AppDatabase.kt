package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

// Tod0 componente do tipo Database tem que herdar da classe RoomDatabase()
// É preciso colacar a notação @Database com os parametros entities e version
// Esta anotação serve para que o Room utilizando o annotation processor consiga gerar o code.
// O entities serve para determinar quais foram as entidades que configuramos dentro do projeto,
// que o Room vai conseguir configurar se comunicar com o SQLite. Neste caso só temos um produto
// e vamos declará-lo dentro do entities. E sempre é esperado um array, entao tem que mandar como array
// e é preciso mandar como uma referência de classe.
// O version recebe um número Int e é basicamente a versão do banco de dado (que implica na técnica de migração,
// que ajusta o banco de dado de conforme mudanças). A classe Produto é a primeira versão do banco de dados
@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class) // referência da classe de conversão
abstract class AppDatabase : RoomDatabase() {
    // Função para o database gerar o código do nosso DAO
    // Use o nome do DAO que vc quer e devolva uma referencia do DAO que está querendo criar
    abstract fun produtoDao(): ProdutoDao

    // É usado ocompanion object para a função não precisar de uma instancia para funcionar
    companion object {
        // Função que cria e da acesso a instancia do banco de dados do AppDatabase
        // Encapsulando a lógica para criar o banco de dado e reaproveitar o código
        fun instancia(context: Context): AppDatabase{
            return Room.databaseBuilder( // Método responsável por criar a instancia do banco de dados
                // Contexto da Activity
                // Sempre que alguém chamar o método vai precisar mandar o context para conseguir rodar
                context,
                // Classe de referência que representa o Database
                AppDatabase::class.java,
                // String que representa o nome do arquivo que será gerado
                // para conter a implementação do banco de dado
                // vamos usar o nome do app simulando uma extensão de banco de dados (facilidade em identificar)
                "orgs.db"
            )
                .allowMainThreadQueries() // Config o AppDatabase para rodar na thread principal (Não é uma boa prática)
                .build() // gerar uma instância do tipo da classe AppDatabase mandada como referencia
        }
    }
}