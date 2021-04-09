package dianaMartine.diana.carros.util;

import dianaMartine.diana.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author diana
 */
public class FabricaConexao {

    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost/sistema-carros";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection getConexao() throws ErroSistema {
        if (conexao == null) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
                } catch (ClassNotFoundException ex) {
                    throw new ErroSistema("Não foi possível conectar ao banco de dados", ex);
                }
            } catch (SQLException ex) {
                throw new ErroSistema("Driver do banco de dados não encontrado", ex);
            }
        }
        return conexao;
    }

    public static void fecharConexao() throws ErroSistema {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar conexão com o bando de dados", ex);
            }
        }
    }
}
