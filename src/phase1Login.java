//PADUA, CARL MITZCHEL
//1CSC - ICS2606
//Final Academic Project

//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.List;

public class phase1Login implements ActionListener
{
    //initializing attributes
    private final JPanel p1;
    private final JFrame loginF;
    private final JButton loginB;
    private final JTextField loginTF1;
    private final JTextField loginTF2;
    private final JLabel username;
    private final JLabel password;
    private BufferedReader br;
    private FileReader fr;
    private String str;
    private final List_of_Records lor = new List_of_Records();
    protected static int ctr = 1;
    protected static int ctr2 = 0;

    private final ArrayList<Person> pC = new ArrayList<>();


    public static void main(String[] args)
    {
        phase1Login pStart = new phase1Login();
        //login screen
        pStart.startApp();
    }

    //constructor of the Login Screen
    public phase1Login()
    {
        loginF = new JFrame("Login Screen");

        p1 = new JPanel();

        loginB = new JButton("Login");
        loginTF1 = new JTextField(15);
        loginTF2 = new JTextField(15);
        username = new JLabel("Username:");
        password = new JLabel("Password:");
    }

    //Initialization and addition of components & container
    public void startApp()
    {
        //Used Null Layout as Layout Manager
        p1.setLayout(null);

        loginF.add(p1);
        loginF.setPreferredSize(new Dimension(400, 200));
        loginF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        username.setBounds(100, 8, 70, 20);
        p1.add(username);

        loginTF1.setBounds(100, 27, 193, 28);
        loginTF1.setEditable(true);
        p1.add(loginTF1);

        password.setBounds(100, 55, 70, 20);
        p1.add(password);

        loginTF2.setBounds(100, 75, 193, 28);
        loginTF2.setEditable(true);
        p1.add(loginTF2);

        loginB.setBounds(150, 120, 90, 25);
        loginB.addActionListener(this);
        p1.add(loginB);

        loginF.pack();
        loginF.setVisible(true);
    }

    //ActionEvent whenever the Login Button is clicked
    @Override
    public void actionPerformed(ActionEvent e)
    {
        BufferedReader br = null;

        /*First, 2 lists are used in order to segregate user info from password info.
         * HashMap used the user list, and password list as its key and value respectively. */
        HashMap<String, String> mapINFO = new HashMap<>();
        List<String> userList = new ArrayList<>();
        List<String> pwList = new ArrayList<>();

        /*Gather the information inputted in the JTextField*/
        String username = loginTF1.getText();
        String password = loginTF2.getText();

        try
        {

            fr = new FileReader("C:/Users/Chy/IdeaProjects/fap/out/production/fap/loginCredentials.txt");
            br = new BufferedReader(fr);

            /*Read the provided .txt file line by line*/
            while ((str = br.readLine()) != null)
            {
                /*If the line is odd, it is placed in the userList, vice versa.*/
                if (ctr % 2 != 0)
                {
                    userList.add(str);
                } else
                {
                    pwList.add(str);
                }
                ctr++;
            }
            /*Close the BufferedReader and the FileReader*/
            br.close();
            fr.close();
        } catch (IOException e2)
        {
            e2.printStackTrace();
        }
        /*Iterator for the lists so that it could be placed one by one in the HashMap*/
        ListIterator<String> userListITR = userList.listIterator();
        ListIterator<String> pwListITR = pwList.listIterator();

        while (userListITR.hasNext() || pwListITR.hasNext())
        {
            mapINFO.put(userListITR.next(), pwListITR.next());
        }

        /*ctr2 represents the attempts of the user when logging in.
         * if the user has less than 3 attempts the next succeeding lines will run.*/
        if (ctr2 <= 3)
        {
            /*Validation if the HashMap contains the inputted username and password through its key and value, respectively.*/
            if (mapINFO.containsKey(username) && mapINFO.containsValue(password))
            {
                /*If permitted, the List of Records will run and the Login Screen will be disposed.*/
                lor.startAppLOR();
                loginF.dispose();

            } else
            {
                /*In the event that the HashMap does not see the inputted user and password in its keys and values, a
                 * JOptionPane will pop up telling the user that the fields are incorrect*/
                JOptionPane.showMessageDialog(null, "Incorrect Username / Password",
                        "Error Screen", JOptionPane.INFORMATION_MESSAGE);

                /*If the inputted information are wrong, the ctr2 will increment by 1*/
                ctr2++;
            }
        }

        /*If the ctr2 reaches 3, the program will launch a different JOptionPane, notifying the user that he or she has
         * reached the maximum attempts which is 3. Then, the program will terminate.*/
        if (ctr2 == 3)
        {
            JOptionPane.showMessageDialog(null, "Sorry, you have reached the limit of 3 tries," +
                    " good bye!", "Error Screen", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    class ascendingName implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return a.getName().compareTo(b.getName());
        }

    }

    class descendingName implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return b.getName().compareTo(a.getName());
        }
    }

    class ascendingBirthday implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return a.getBirthDay().compareTo(b.getBirthDay());
        }

    }

    class descendingBirthday implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return b.getBirthDay().compareTo(a.getBirthDay());
        }
    }

    class ascendingAge implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return a.getAge()-(b.getAge());
        }
    }

    class descendingAge implements Comparator <Person>
    {
        public int compare(Person a, Person b)
        {
            return b.getAge()-(a.getAge());
        }
    }

    /*Start of Phase 2*/
    private class List_of_Records extends JPanel
    {
        //declaring variables
        private final JTextArea resultsListRecords;
        private final JScrollPane resultsListRecordsScroll;
        private final JComboBox jbc_Sort;
        private final JRadioButton rb_Ascending;
        private final JRadioButton rb_Descending;
        private final ButtonGroup group;
        private final JButton ADD_addRecord;
        private final JButton REM_removeRecord;
        private final JButton exportCSV;
        private final JLabel sortBy;

        public List_of_Records()
        {
            //JComboBox initialization
            String[] jbc_SortItems = {"Name", "Birthday", "Age"};

            //creating the components
            resultsListRecords = new JTextArea("\tNAME\tBIRTHDAY\tAGE\t");
            resultsListRecords.setEditable(false);

            resultsListRecordsScroll = new JScrollPane(resultsListRecords, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


            jbc_Sort = new JComboBox(jbc_SortItems);
            jbc_Sort.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String item = (String) jbc_Sort.getItemAt(0);

                    if(jbc_Sort.getSelectedItem().toString().equals("Name") && group.getSelection().getActionCommand().equals("Ascending"))
                    {
                        System.out.println("Sorting by name in ascending:");
                        Collections.sort(pC, new ascendingName());

                        String s = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iter = pC.listIterator();
                        while (iter.hasNext())
                        {
                            Person n = iter.next();
                            String b = n.getBirthDay().getMonth() + "/" + n.getBirthDay().getDate() + "/" + n.getBirthDay().getYear();

                            s += "\n\n\t" + n.getName();
                            s += "\t" + b;
                            s += "\t" + n.getAge();
                        }
                        lor.resultsListRecords.setText(s);
                    }

                    if(jbc_Sort.getSelectedItem().toString().equals("Name") && group.getSelection().getActionCommand().equals("Descending"))
                    {
                        System.out.println("Sorting by name in descending:");
                        Collections.sort(pC, new descendingName());

                        String sDescName = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iterDescName = pC.listIterator();
                        while (iterDescName.hasNext())
                        {
                            Person nDescName = iterDescName.next();
                            String b = nDescName.getBirthDay().getMonth() + "/" + nDescName.getBirthDay().getDate() +
                                    "/" + nDescName.getBirthDay().getYear();

                            sDescName += "\n\n\t" + nDescName.getName();
                            sDescName += "\t" + b;
                            sDescName += "\t" + nDescName.getAge();
                        }
                        lor.resultsListRecords.setText(sDescName);
                    }
                    if(jbc_Sort.getSelectedItem().toString().equals("Birthday") && group.getSelection().getActionCommand().equals("Ascending"))
                    {
                        System.out.println("Sorting by birthday in ascending:");
                        Collections.sort(pC, new ascendingBirthday());

                        String sAscBday = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iterAscBday = pC.listIterator();
                        while (iterAscBday.hasNext())
                        {
                            Person nAscBday = iterAscBday.next();
                            String b = nAscBday.getBirthDay().getMonth() + "/" + nAscBday.getBirthDay().getDate() +
                                    "/" + nAscBday.getBirthDay().getYear();

                            sAscBday += "\n\n\t" + nAscBday.getName();
                            sAscBday += "\t" + b;
                            sAscBday += "\t" + nAscBday.getAge();
                        }
                        lor.resultsListRecords.setText(sAscBday);
                    }
                    if(jbc_Sort.getSelectedItem().toString().equals("Birthday") && group.getSelection().getActionCommand().equals("Descending"))
                    {
                        System.out.println("Sorting by birthday in descending:");
                        Collections.sort(pC, new descendingBirthday());

                        String sDescBday = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iterDescBday = pC.listIterator();
                        while (iterDescBday.hasNext())
                        {
                            Person nDescBday = iterDescBday.next();
                            String b = nDescBday.getBirthDay().getMonth() + "/" + nDescBday.getBirthDay().getDate() +
                                    "/" + nDescBday.getBirthDay().getYear();

                            sDescBday += "\n\n\t" + nDescBday.getName();
                            sDescBday += "\t" + b;
                            sDescBday += "\t" + nDescBday.getAge();
                        }
                        lor.resultsListRecords.setText(sDescBday);
                    }
                    if(jbc_Sort.getSelectedItem().toString().equals("Age") && group.getSelection().getActionCommand().equals("Ascending"))
                    {
                        System.out.println("Sorting by age in ascending:");
                        Collections.sort(pC, new ascendingAge());

                        String sAscAge = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iterAscAge = pC.listIterator();
                        while (iterAscAge.hasNext())
                        {
                            Person nAscAge = iterAscAge.next();
                            String b = nAscAge.getBirthDay().getMonth() + "/" + nAscAge.getBirthDay().getDate() +
                                    "/" + nAscAge.getBirthDay().getYear();

                            sAscAge += "\n\n\t" + nAscAge.getName();
                            sAscAge += "\t" + b;
                            sAscAge += "\t" + nAscAge.getAge();
                        }
                        lor.resultsListRecords.setText(sAscAge);
                    }
                    if(jbc_Sort.getSelectedItem().toString().equals("Age") && group.getSelection().getActionCommand().equals("Descending"))
                    {
                        System.out.println("Sorting by age in descending:");
                        Collections.sort(pC, new descendingAge());

                        String sDescAge = "\tNAME\tBIRTHDAY\tAGE\t";

                        Iterator<Person> iterDescAge = pC.listIterator();
                        while (iterDescAge.hasNext())
                        {
                            Person nDescAge = iterDescAge.next();
                            String b = nDescAge.getBirthDay().getMonth() + "/" + nDescAge.getBirthDay().getDate() +
                                    "/" + nDescAge.getBirthDay().getYear();

                            sDescAge += "\n\n\t" + nDescAge.getName();
                            sDescAge += "\t" + b;
                            sDescAge += "\t" + nDescAge.getAge();
                        }
                        lor.resultsListRecords.setText(sDescAge);
                    }
                }
            });

            rb_Ascending = new JRadioButton("Ascending");
            rb_Ascending.setActionCommand("Ascending");

            rb_Descending = new JRadioButton("Descending");
            rb_Descending.setActionCommand("Descending");

            group = new ButtonGroup();
            group.add(rb_Ascending);
            group.add(rb_Descending);

            ADD_addRecord = new JButton(new AbstractAction("Add a Record")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Add_Records ar = new Add_Records();
                    ar.startAppAR();
                }
            });

            REM_removeRecord = new JButton(new AbstractAction("Remove a Record")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Remove_Record rr = new Remove_Record();
                    rr.startAppRR();
                }
            });
            exportCSV = new JButton(new AbstractAction("Export to CSV File")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //set date format
                    Date date = new Date(); //create an instance of current date
                    String CSVFileName = dateFormat.format(date)  + ""; // name csvfile

                    //remove ":", "/", and space then adds .csv for file name
                    CSVFileName = CSVFileName.replaceAll(":","");
                    CSVFileName = CSVFileName.replaceAll("/","");
                    CSVFileName = CSVFileName.replaceAll(" ","");
                    CSVFileName += ".csv";
                    //create CSVFile
                    try {
                        FileWriter fw = new FileWriter(CSVFileName, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);

                        for(int i = 0; i <pC.size(); i++)
                        {
                            String b = pC.get(i).getBirthDay().getMonth() + "/" + pC.get(i).getBirthDay().getDate() + "/" + pC.get(i).getBirthDay().getYear();
                            pw.println(pC.get(i).getName() + "," + b + "," + pC.get(i).getAge());

                        }
                        JOptionPane.showMessageDialog(null, "File has been exported to CSV successfully!",
                                "Export to CSV", JOptionPane.INFORMATION_MESSAGE);
                        pw.flush();
                        pw.close();

                    } catch (IOException e_export) { //display error message
                        JOptionPane.showMessageDialog(null, "File save unsucessful",
                                "Error Screen", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            sortBy = new JLabel("Sort by:");
        }

        //constructor and start app in one method
        public void startAppLOR()
        {
            JFrame frame = new JFrame("List of Records");

            //setting preferred dimension of the gui and the layout
            frame.setPreferredSize(new Dimension(455, 490));
            frame.setLayout(null);

            //setting the boundaries in order to set apart the components from one another
            resultsListRecordsScroll.setBounds(20, 20, 400, 255);
            jbc_Sort.setBounds(320, 285, 100, 25);
            rb_Ascending.setBounds(15, 285, 100, 25);
            rb_Descending.setBounds(115, 285, 100, 25);
            ADD_addRecord.setBounds(20, 325, 400, 30);
            REM_removeRecord.setBounds(20, 365, 400, 30);
            exportCSV.setBounds(20, 405, 400, 30);
            sortBy.setBounds(270, 285, 100, 25);

            //adding the components
            frame.add(resultsListRecordsScroll);

            frame.add(jbc_Sort);

            frame.add(rb_Ascending);

            frame.add(rb_Descending);

            frame.add(ADD_addRecord);

            frame.add(REM_removeRecord);

            frame.add(exportCSV);

            frame.add(sortBy);

            /*This allows the user to close the window via its close button in the upper right corner*/
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

        /*This method appends the List of Records the name, birthday, and the age*/
        void display(String name)
        {
            resultsListRecords.setText(name);
        }

    }

    /*Another window that can be accessed when the user wants to Add a Record*/
    private class Add_Records
    {

        //declaring variables
        private final JLabel p2_Label_name;
        private final JLabel p2_label_birthday;
        private final JTextField p2_tf;
        private final JComboBox p2_jcb_mm;
        private final JComboBox p2_jcb_dd;
        private final JComboBox p2_jcb_yyyy;
        private final JLabel p2_label_mm;
        private final JLabel p2_label_dd;
        private final JLabel p2_label_yyyy;
        private final JButton p2_b_saveBack;
        private final JButton p2_b_saveAdd;
        private final JButton p2_b_back;
        private final JFrame frame = new JFrame("Add Records");

        //constructor and start app in one method
        public Add_Records()
        {
            //JComboBox initialization
            String[] p2_jcb_mmItems = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String[] p2_jcb_ddItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                    "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
            String[] p2_jcb_yyyyItems = {"1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909",
                    "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922",
                    "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935",
                    "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948",
                    "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961",
                    "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974",
                    "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987",
                    "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000",
                    "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
                    "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

            //creating the components
            p2_Label_name = new JLabel("Name:");
            p2_label_birthday = new JLabel("Birthday");
            p2_tf = new JTextField(5);
            p2_jcb_mm = new JComboBox(p2_jcb_mmItems);
            p2_jcb_dd = new JComboBox(p2_jcb_ddItems);
            p2_jcb_yyyy = new JComboBox(p2_jcb_yyyyItems);
            p2_label_mm = new JLabel("mm");
            p2_label_dd = new JLabel("dd");
            p2_label_yyyy = new JLabel("yyyy");

            //using anonymous classes to make an ActionListener to the button
            p2_b_saveBack = new JButton(new AbstractAction("Save and Go Back")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Person p = new Person();
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                    TemporalAccessor accessor = parser.parse(p2_jcb_mm.getSelectedItem().toString());

                    int month = accessor.get(ChronoField.MONTH_OF_YEAR);

                    int day = Integer.parseInt(p2_jcb_dd.getSelectedItem().toString());

                    int year = Integer.parseInt(p2_jcb_yyyy.getSelectedItem().toString());

                    String name = p2_tf.getText();
                    p.setName(name);

                    p.setBirthDay(new Date(year, month, day));
                    p.setAge(p.computeAge(p.getBirthDay()));

                    pC.add(p);

                    String s = "\tNAME\tBIRTHDAY\tAGE\t";


                    Iterator<Person> iter = pC.listIterator();
                    while (iter.hasNext())
                    {
                        Person n = iter.next();
                        String b = n.getBirthDay().getMonth() + "/" + n.getBirthDay().getDate() + "/" + n.getBirthDay().getYear();

                        s += "\n\n\t" + n.getName();
                        s += "\t" + b;
                        s += "\t" + n.getAge();
                    }

                    lor.resultsListRecords.setText(s);

                    frame.dispose();
                }
            });

            p2_b_saveAdd = new JButton(new AbstractAction("Save & Add Another")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Person p = new Person();
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                    TemporalAccessor accessor = parser.parse(p2_jcb_mm.getSelectedItem().toString());

                    int month = accessor.get(ChronoField.MONTH_OF_YEAR);

                    int day = Integer.parseInt(p2_jcb_dd.getSelectedItem().toString());

                    int year = Integer.parseInt(p2_jcb_yyyy.getSelectedItem().toString());

                    String name = p2_tf.getText();
                    p.setName(name);

                    p.setBirthDay(new Date(year, month, day));
                    p.setAge(p.computeAge(p.getBirthDay()));

                    pC.add(p);

                    String s = "\tNAME\tBIRTHDAY\tAGE\t";


                    Iterator<Person> iter = pC.listIterator();
                    while (iter.hasNext())
                    {
                        Person n = iter.next();
                        String b = n.getBirthDay().getMonth() + "/" + n.getBirthDay().getDate() + "/" + n.getBirthDay().getYear();

                        s += "\n\n\t" + n.getName();
                        s += "\t" + b;
                        s += "\t" + n.getAge();
                    }

                    lor.resultsListRecords.setText(s);

                    p2_tf.setText("");
                }
            });

            p2_b_back = new JButton(new AbstractAction("Back")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    frame.dispose();
                }
            });
        }

        public void startAppAR()
        {

            //setting preferred dimension of the gui and the layout
            frame.setPreferredSize(new Dimension(498, 320));
            frame.setLayout(null);

            //setting the boundaries in order to set apart the components from one another
            p2_Label_name.setBounds(40, 45, 100, 25);
            p2_label_birthday.setBounds(40, 85, 100, 25);
            p2_tf.setBounds(85, 45, 370, 25);
            p2_jcb_mm.setBounds(95, 85, 100, 25);
            p2_jcb_dd.setBounds(210, 85, 100, 25);
            p2_jcb_yyyy.setBounds(325, 85, 100, 25);
            p2_label_mm.setBounds(130, 115, 100, 25);
            p2_label_dd.setBounds(250, 115, 100, 25);
            p2_label_yyyy.setBounds(360, 115, 100, 25);
            p2_b_saveBack.setBounds(40, 165, 245, 30);
            p2_b_saveAdd.setBounds(290, 165, 160, 30);
            p2_b_back.setBounds(40, 205, 410, 30);

            //adding the components
            frame.add(p2_Label_name);

            frame.add(p2_label_birthday);

            frame.add(p2_tf);

            frame.add(p2_jcb_mm);
            p2_jcb_mm.setEditable(true);

            frame.add(p2_jcb_dd);
            p2_jcb_dd.setEditable(true);

            frame.add(p2_jcb_yyyy);
            p2_jcb_yyyy.setEditable(true);

            frame.add(p2_label_mm);

            frame.add(p2_label_dd);

            frame.add(p2_label_yyyy);

            frame.add(p2_b_saveBack);

            frame.add(p2_b_saveAdd);

            frame.add(p2_b_back);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.pack();
            frame.setVisible(true);
        }

    }

    private class Remove_Record
    {
        //declaring variables
        private final JLabel rr_nameLabel;
        private final JTextField rr_textField;
        private final JButton rr_removeBack;
        private final JButton rr_saveRemove;
        private final JButton rr_back;
        private final JFrame frame;
        private String input;

        //constructor and start app in one method
        public Remove_Record()
        {
            //creating the components
            frame = new JFrame("Remove Record");
            rr_nameLabel = new JLabel("Name:");
            rr_textField = new JTextField(5);
            rr_removeBack = new JButton(new AbstractAction("Remove and Go Back")
            {
                @Override
                public void actionPerformed(ActionEvent e) throws IllegalArgumentException
                {
                    Iterator<Person> itr = pC.listIterator();
                    String s = "\tNAME\tBIRTHDAY\tAGE\t";
                    input = rr_textField.getText();

                    Iterator<Person> iterator = pC.iterator();
                    try
                    {
                        boolean flag = false;

                        for(int i = 0 ; i<pC.size(); i++)
                        {
                            if(rr_textField.getText().equals(pC.get(i).getName()))
                            {
                                pC.remove(i);
                                flag=true;
                            }
                        }
                        if(flag==false)
                        {
                            throw new IllegalArgumentException ("IllegalArgumentException Caught: Name not found");
                        }
                    } catch (IllegalArgumentException e_rem)
                    {
                        JOptionPane.showMessageDialog(null, e_rem.getMessage(), "Error Screen",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    Iterator<Person> iter = pC.listIterator();
                    while (iter.hasNext())
                    {
                        Person n = iter.next();
                        String b = n.getBirthDay().getMonth() + "/" + n.getBirthDay().getDate() + "/" + n.getBirthDay().getYear();

                        s += "\n\n\t" + n.getName();
                        s += "\t" + b;
                        s += "\t" + n.getAge();
                    }
                    lor.resultsListRecords.setText(s);

                    frame.dispose();
                }
            });

            rr_saveRemove = new JButton(new AbstractAction("Save & Remove Another")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Iterator<Person> itr = pC.listIterator();
                    String s = "\tNAME\tBIRTHDAY\tAGE\t";
                    input = rr_textField.getText();

                    Iterator<Person> iterator = pC.iterator();
                    try
                    {
                        boolean flag = false;

                        for(int i = 0 ; i<pC.size(); i++)
                        {
                            if(rr_textField.getText().equals(pC.get(i).getName()))
                            {
                                pC.remove(i);
                                flag=true;
                            }
                        }
                        if(flag==false)
                        {
                            throw new IllegalArgumentException ("IllegalArgumentException Caught: Name not found");
                        }
                    } catch (IllegalArgumentException e_rem)
                    {
                        JOptionPane.showMessageDialog(null, e_rem.getMessage(), "Error Screen",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    Iterator<Person> iter = pC.listIterator();
                    while (iter.hasNext())
                    {
                        Person n = iter.next();
                        String b = n.getBirthDay().getMonth() + "/" + n.getBirthDay().getDate() + "/" + n.getBirthDay().getYear();

                        s += "\n\n\t" + n.getName();
                        s += "\t" + b;
                        s += "\t" + n.getAge();
                    }
                    lor.resultsListRecords.setText(s);
                    rr_textField.setText("");
                }
            });
            rr_back = new JButton(new AbstractAction("Back")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    frame.dispose();
                }
            });
        }

        public void startAppRR()
        {
            //setting preferred dimension of the gui and the layout
            frame.setPreferredSize(new Dimension(498, 220));
            frame.setLayout(null);

            //setting the boundaries in order to set apart the components from one another
            rr_nameLabel.setBounds(55, 35, 100, 25);
            rr_textField.setBounds(105, 35, 325, 25);
            rr_removeBack.setBounds(55, 85, 195, 25);
            rr_saveRemove.setBounds(255, 85, 175, 25);
            rr_back.setBounds(55, 125, 375, 25);

            //adding the components
            frame.add(rr_nameLabel);
            frame.add(rr_textField);
            frame.add(rr_removeBack);
            frame.add(rr_saveRemove);
            frame.add(rr_back);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.pack();
            frame.setVisible(true);
        }
    }

    /*The class person*/
    public class Person
    {
        /*Attributes*/
        private String name = null;
        private Date birthDay = null;
        private int age = 0;

        /*Constructor 1*/
        Person()
        {

        }

        /*Constructor 2*/
        Person(String name, Date birthDay, int age)
        {
            this.name = name;
            this.birthDay = birthDay;
            this.age = age;
        }

        /*computeAge method*/
        int computeAge(Date birthDay)
        {
            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.of(birthDay.getYear(), birthDay.getMonth(), birthDay.getDate());
            Period p = Period.between(birthday, today);
            int age = p.getYears();

            return age;
        }

        /*Getters and Setters*/
        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Date getBirthDay()
        {
            return birthDay;
        }

        public void setBirthDay(Date birthDay)
        {
            this.birthDay = birthDay;
        }

        public int getAge()
        {
            return age;
        }

        public void setAge(int age)
        {
            this.age = age;
        }
    }
}


