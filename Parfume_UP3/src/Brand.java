import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Brand {
    int id; String name;
    public Brand(int id, String name) { this.id = id; this.name = name; }
}

class BrandDAO {
    public List<Brand> getAll() {
        List<Brand> list = new ArrayList<>();
        try (Connection c = Database.getConnection(); Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM brand")) {
            while (rs.next()) list.add(new Brand(rs.getInt("id_brand"), rs.getString("name")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Doljnost {
    int id; String name; double oklad; String objazannosty;
    public Doljnost(int id, String name, double oklad, String objazannosty) {
        this.id = id; this.name = name; this.oklad = oklad; this.objazannosty = objazannosty;
    }
}

class DoljnostDAO {
    public List<Doljnost> getAll() {
        List<Doljnost> list = new ArrayList<>();
        try (Connection c = Database.getConnection(); Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM doljnost")) {
            while (rs.next()) list.add(new Doljnost(rs.getInt("id_doljnost"), rs.getString("name"), rs.getDouble("oklad"), rs.getString("objazannosty")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Client {
    int id; String familia; String name; String telefon; String loyaltyCard;
    public Client(int id, String fam, String name, String telefon, String loyaltyCard) {
        this.id = id; this.familia = fam; this.name = name; this.telefon = telefon; this.loyaltyCard = loyaltyCard;
    }
}

class ClientDAO {
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        try (Connection c = Database.getConnection(); Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM client")) {
            while (rs.next()) list.add(new Client(rs.getInt("id_client"), rs.getString("familia"), rs.getString("name"), rs.getString("telefon"), rs.getString("loyalty_card")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Sotrudnik {
    int id; String doljnostName; String name; String familia; String otchestvo; String telefon;
    public Sotrudnik(int id, String doljnostName, String name, String fam, String otchestvo, String telefon) {
        this.id = id; this.doljnostName = doljnostName; this.name = name; this.familia = fam; this.otchestvo = otchestvo; this.telefon = telefon;
    }
}

class SotrudnikDAO {
    public List<Sotrudnik> getAll() {
        List<Sotrudnik> list = new ArrayList<>();
        String sql = "SELECT s.id_sotr, d.name AS dolj_name, s.name, s.familia, s.otchestvo, s.telefon " +
                     "FROM sotrudnik s LEFT JOIN doljnost d ON s.id_doljnost = d.id_doljnost";
        try (Connection c = Database.getConnection(); Statement stat = c.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) list.add(new Sotrudnik(rs.getInt("id_sotr"), rs.getString("dolj_name"), rs.getString("name"), rs.getString("familia"), rs.getString("otchestvo"), rs.getString("telefon")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Parfum {
    int id; String brandName; String aromat; int vol; int qty; double price;
    public Parfum(int id, String brandName, String aromat, int vol, int qty, double price) {
        this.id = id; this.brandName = brandName; this.aromat = aromat; this.vol = vol; this.qty = qty; this.price = price;
    }
}

class ParfumDAO {
    public List<Parfum> getAll() {
        List<Parfum> list = new ArrayList<>();
        String sql = "SELECT p.id_parfum, b.name AS brand_name, p.aromat_name, p.volume, p.quantity, p.price " +
                     "FROM parfum p LEFT JOIN brand b ON p.id_brand = b.id_brand";
        try (Connection c = Database.getConnection(); Statement stat = c.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) list.add(new Parfum(rs.getInt("id_parfum"), rs.getString("brand_name"), rs.getString("aromat_name"), rs.getInt("volume"), rs.getInt("quantity"), rs.getDouble("price")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Zakaz {
    int id; String sotrFam; String clientFamilia; String parfumName; String date; String oplata; double sum;
    public Zakaz(int id, String sotrFam, String clientFam, String parfumName, String date, String oplata, double sum) {
        this.id = id; this.sotrFam = sotrFam; this.clientFamilia = clientFam; this.parfumName = parfumName; this.date = date; this.oplata = oplata; this.sum = sum;
    }
}

class ZakazDAO {
    public List<Zakaz> getAll() {
        List<Zakaz> list = new ArrayList<>();
        String sql = "SELECT z.id_zakaza, s.familia AS sotr_familia, c.familia AS client_familia, p.aromat_name, z.data_zakaza, z.oplata, z.summa_zakaza " +
                     "FROM zakaz z " +
          "LEFT JOIN sotrudnik s ON z.id_sotr = s.id_sotr " +
          "LEFT JOIN client c ON z.id_client = c.id_client " +
          "LEFT JOIN parfum p ON z.id_parfum = p.id_parfum";
        try (Connection c = Database.getConnection(); Statement stat = c.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) list.add(new Zakaz(rs.getInt("id_zakaza"), rs.getString("sotr_familia"), rs.getString("client_familia"), rs.getString("aromat_name"), rs.getString("data_zakaza"), rs.getString("oplata"), rs.getDouble("summa_zakaza")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
