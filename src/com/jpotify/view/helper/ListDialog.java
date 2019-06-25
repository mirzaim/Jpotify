package com.jpotify.view.helper;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;



public class ListDialog {
    private JList list;
    private DefaultListModel<String> listModel;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    private ArrayList<String> finalArrayList;

    public ListDialog(String message, JList listToDisplay, DefaultListModel<String> listModel) {
        list = listToDisplay;
        this.listModel = listModel;
        label = new JLabel(message);
        createAndDisplayOptionPane();
        MyMouseAdaptor myMouseAdaptor = new MyMouseAdaptor();
        list.addMouseListener(myMouseAdaptor);
        list.addMouseMotionListener(myMouseAdaptor);
    }

    public ListDialog(String title, String message, JList listToDisplay, DefaultListModel<String> listModel) {
        this(message, listToDisplay, listModel);
        dialog.setTitle(title);
    }

    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Select option");
    }

    private void setupButtons() {
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents() {
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }

    public void setOnClose(ActionListener event) {
        cancelEvent = event;
    }

    private void handleOkButtonClick(ActionEvent e) {
        if (okEvent != null) {
            okEvent.actionPerformed(e);
        }
        hide();
    }

    private void handleCancelButtonClick(ActionEvent e) {
        if (cancelEvent != null) {
            cancelEvent.actionPerformed(e);
        }
        hide();
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void hide() {
        dialog.setVisible(false);
    }

    public Object getSelectedItem() {
        return list.getSelectedValue();
    }

    private class MyMouseAdaptor extends MouseInputAdapter {
        private boolean mouseDragging = false;
        private int dragSourceIndex;

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                dragSourceIndex = list.getSelectedIndex();
                mouseDragging = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseDragging = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (mouseDragging) {
                int currentIndex = list.locationToIndex(e.getPoint());
                if (currentIndex != dragSourceIndex) {
                    int dragTargetIndex = list.getSelectedIndex();
                    String dragElement = listModel.get(dragSourceIndex);
                    listModel.remove(dragSourceIndex);
                    listModel.add(dragTargetIndex, dragElement);
                    dragSourceIndex = currentIndex;
                }
            }
        }
    }

    public String[] convert2SringArray() {
        ArrayList<String> convertedList = new ArrayList<>();
        for (int i = 0; i < list.getModel().getSize(); i++) {
            convertedList.add((String) list.getModel().getElementAt(i));
        }
        return convertedList.toArray(new String[0]);
    }

//    public static void main(String[] args) {
//
//        ArrayList<String> newList = new ArrayList<>();
//
//        DefaultListModel<String> myListModel = createStringListModel(new String[]{"Cat", "Dog", "Cow", "Horse", "Pig", "Monkey"});
//        JList<String> myList = new JList<String>(myListModel);
//
//        ListDialog dialog = new ListDialog("Please select an item in the list: ", myList, myListModel);
//        dialog.setOnOk(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dialog.convert2ArrayList().toArray(new String[0]);
//            }
//        });
//        dialog.show();
//        newList = dialog.convert2ArrayList();
//        for (String s: newList) {
//            System.out.print(s + "                ");
//        }
//
//    }

    public ArrayList<String> setArray(String[] strings) {
        for (String s : strings)
            this.finalArrayList.add(s);
        return finalArrayList;

    }

    static private DefaultListModel<String> createStringListModel(String [] strings) {
        final String[] listElements = strings;
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (String element : listElements) {
            listModel.addElement(element);
        }
        return listModel;
    }
}