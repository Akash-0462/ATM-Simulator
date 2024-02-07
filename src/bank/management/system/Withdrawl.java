package bank.management.system;

import com.mysql.cj.protocol.Resultset;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    String pin;
    TextField textField;
    JButton b1,b2;

    Withdrawl(String pin){

        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,850, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,1500,850);
        add(image);

        JLabel label1 =new JLabel(" MAXIMUM WITHDRAWL IS RS.10,000 ");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System",Font.BOLD,16));
        label1.setBounds(420,180,400,35);
        image.add(label1);

        JLabel label2 =new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System",Font.BOLD,16));
        label2.setBounds(420,210,400,35);
        image.add(label2);

        textField = new TextField();
        textField.setBackground(new Color(65,125,128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(420,250,320,25);
        textField.setFont(new Font("Rraleway",Font.BOLD,20));
        image.add(textField);

        b1 = new JButton("WITHDRAL");
        b1.setBounds(680,373,150,35);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        image.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(680,420,150,35);
        b2.setBackground(new Color(65,125,128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        image.add(b2);

        setLayout(null);
        setSize(1380,1080);
        setLocation(0,0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b1) {

        try {
            String amount = textField.getText();
            Date date = new Date();
            if (textField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Withdra");
            } else {
                Conn c = new Conn();
                ResultSet resultSet = c.statement.executeQuery("select *from bank where pin = '" + pin + "'");
                int balance = 0;
                while (resultSet.next()) {
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }
                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficiant Balance");
                    return;
                }
                c.statement.executeUpdate("insert into bank values('" + pin + "','" + date + "','Withdrawl','" + amount + "')");
                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debitet Successfull");
                setVisible(false);
                new main_class(pin);
            }

        } catch (Exception E) {

        }
    }else if (e.getSource()==b2){
            setVisible(false);
            new main_class(pin);
        }
    }

    public static void main(String args[]){

        new Withdrawl("");
    }

}
