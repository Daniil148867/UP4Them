import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Parfume extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable brandTable, doljnostTable, clientTable, sotrTable, parfumTable, zakazTable;

    public Parfume() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Магазин парфюмерии – Панель управления");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel titleLabel = new JLabel("Система управления базой данных: Магазин парфюмерии");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        brandTable = createBeautifulTable();
        doljnostTable = createBeautifulTable();
        clientTable = createBeautifulTable();
        sotrTable = createBeautifulTable();
        parfumTable = createBeautifulTable();
        zakazTable = createBeautifulTable();
        tabbedPane.addTab("  Бренды  ", new JScrollPane(brandTable));
        tabbedPane.addTab("  Должности  ", new JScrollPane(doljnostTable));
        tabbedPane.addTab("  Клиенты  ", new JScrollPane(clientTable));
        tabbedPane.addTab("  Сотрудники  ", new JScrollPane(sotrTable));
        tabbedPane.addTab("  Парфюм  ", new JScrollPane(parfumTable));
        tabbedPane.addTab("  Заказы  ", new JScrollPane(zakazTable));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        JButton btnUpdate = createBeautifulButton("Обновить данные", new Color(46, 204, 113));
        JButton btnExit = createBeautifulButton("Выйти из системы", new Color(231, 76, 60));

        btnUpdate.addActionListener(e -> loadData());
        btnExit.addActionListener(e -> System.exit(0));

        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnExit);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();
    }

    private JTable createBeautifulTable() {
        JTable table = new JTable();
        table.setRowHeight(35); 
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 230, 230)); 
        table.setSelectionBackground(new Color(52, 152, 219)); 
        table.setSelectionForeground(Color.WHITE);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        return table;
    }
    private void centerTableText(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private JButton createBeautifulButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


    private void loadData() {
    	
        List<Brand> brands = new BrandDAO().getAll();
        DefaultTableModel brandModel = new DefaultTableModel(new String[]{"ID Бренда", "Название бренда"}, 0);
        for (Brand b : brands) brandModel.addRow(new Object[]{b.id, b.name});
        brandTable.setModel(brandModel);
        centerTableText(brandTable);

        List<Doljnost> doljnosti = new DoljnostDAO().getAll();
        DefaultTableModel doljnostModel = new DefaultTableModel(new String[]{"ID", "Должность", "Оклад (руб.)", "Обязанности"}, 0);
        for (Doljnost d : doljnosti) doljnostModel.addRow(new Object[]{d.id, d.name, d.oklad, d.objazannosty});
        doljnostTable.setModel(doljnostModel);
        centerTableText(doljnostTable);

        List<Client> clients = new ClientDAO().getAll();
        DefaultTableModel clientModel = new DefaultTableModel(new String[]{"ID", "Фамилия", "Имя", "Телефон", "Номер карты лояльности"}, 0);
        for (Client c : clients) clientModel.addRow(new Object[]{c.id, c.familia, c.name, c.telefon, c.loyaltyCard});
        clientTable.setModel(clientModel);
        centerTableText(clientTable);

        List<Sotrudnik> sotrudniki = new SotrudnikDAO().getAll();
        DefaultTableModel sotrModel = new DefaultTableModel(new String[]{"ID", "Должность", "Фамилия", "Имя", "Отчество", "Телефон"}, 0);
        for (Sotrudnik s : sotrudniki) sotrModel.addRow(new Object[]{s.id, s.doljnostName, s.familia, s.name, s.otchestvo, s.telefon});
        sotrTable.setModel(sotrModel);
        centerTableText(sotrTable);

        List<Parfum> parfums = new ParfumDAO().getAll();
        DefaultTableModel parfumModel = new DefaultTableModel(new String[]{"ID", "Бренд", "Название аромата", "Объем (мл)", "Количество", "Цена (руб.)"}, 0);
        for (Parfum p : parfums) parfumModel.addRow(new Object[]{p.id, p.brandName, p.aromat, p.vol, p.qty, p.price});
        parfumTable.setModel(parfumModel);
        centerTableText(parfumTable);

        List<Zakaz> zakazy = new ZakazDAO().getAll();
        DefaultTableModel zakazModel = new DefaultTableModel(new String[]{"№ Заказа", "Фамилия Сотрудника", "Фамилия Клиента", "Аромат", "Дата заказа", "Статус оплаты", "Итоговая сумма"}, 0);
        for (Zakaz z : zakazy) zakazModel.addRow(new Object[]{z.id, z.sotrFam, z.clientFamilia, z.parfumName, z.date, z.oplata, z.sum});
        zakazTable.setModel(zakazModel);
        centerTableText(zakazTable);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Parfume().setVisible(true));
    }
}
