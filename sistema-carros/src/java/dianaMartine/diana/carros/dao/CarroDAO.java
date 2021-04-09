package dianaMartine.diana.carros.dao;

import dianaMartine.diana.carros.entidades.Carro;
import dianaMartine.diana.carros.util.FabricaConexao;
import dianaMartine.diana.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diana
 */
public class CarroDAO implements CrudDAO<Carro>{
    
    @Override
    public void salvar(Carro car) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            if (car.getId() == null) {
                ps = conexao.prepareCall("INSERT INTO `carro`(`model`,`manufacturer`,`color`,`year`) VALUES (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update carro set model=?, manufacturer=?, color=?, year=? where id=?");
                ps.setInt(5, car.getId());
            }
            ps.setString(1, car.getModel());
            ps.setString(2, car.getManufacturer());
            ps.setString(3, car.getColor());
            ps.setInt(4, car.getYear());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao salvar", ex);
        }
    }

    public void deletar(Carro car) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delet from carro where id = ?");
            ps.setInt(1, car.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o item", ex);
        }
    }

     @Override
    public List<Carro> buscar() throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from carro");
            ResultSet resultSet = ps.executeQuery();
            List<Carro> cars = new ArrayList<>();

            while (resultSet.next()) {
                Carro car = new Carro();
                car.setId(resultSet.getInt("id"));
                car.setModel(resultSet.getString("modelo"));
                car.setManufacturer(resultSet.getString("manufacturer"));
                car.setColor(resultSet.getString("color"));
                car.setYear(resultSet.getInt("year"));
                cars.add(car);
            }

            FabricaConexao.fecharConexao();
            return cars;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar item", ex);
        }
    }
}
