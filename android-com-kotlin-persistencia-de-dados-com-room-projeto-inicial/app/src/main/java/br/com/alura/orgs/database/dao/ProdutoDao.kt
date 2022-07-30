package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

// Pacote específico para config no banco de dados

@Dao
interface ProdutoDao {

    // A notação @Query é um comportamento para buscar registros,
    // no nosso caso, uma lista de produto.
    // O que está dentro de ( ) é o SQL que esperamos para buscar todos os produtos
    @Query("Select * FROM Produto")
    fun buscaTodos() : List<Produto>

    // A notação @Insert é para inserir, onde passamos o que queremos inserir,
    // que é nosso produto. Com isso podemos salvar infos no nosso banco de dados.
    // O vararg é para mandar (salvar neste caso) mais de um registro
    @Insert
    fun salva(vararg produto: Produto)

    //Código que irá remover uma entidade no banco de dados (neste caso um produto)
    @Delete
    fun remove(produto: Produto)

    // O remove e altera sempre vai fazer a alteração devido o id
    @Update
    fun altera(produto: Produto)
}