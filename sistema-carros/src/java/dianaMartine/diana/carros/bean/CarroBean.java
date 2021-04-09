package dianaMartine.diana.carros.bean;

import dianaMartine.diana.carros.dao.CarroDAO;
import dianaMartine.diana.carros.entidades.Carro;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@javax.faces.bean.ManagedBean
@SessionScoped

public class CarroBean extends CrudBean<Carro, CarroDAO> {
    
    private CarroDAO carroDAO;

    @Override
    public CarroDAO getDAO() {
        if(carroDAO == null) {
            carroDAO = new CarroDAO();
        }
        return carroDAO;
    }

    @Override
    public Carro criarNovaEntidade() {
        return new Carro();
    }
}
