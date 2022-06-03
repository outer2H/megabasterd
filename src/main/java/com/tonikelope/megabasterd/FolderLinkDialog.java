package com.tonikelope.megabasterd;

import static com.tonikelope.megabasterd.MainPanel.*;
import static com.tonikelope.megabasterd.MiscTools.*;
import java.awt.Dialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author tonikelope
 */
public class FolderLinkDialog extends javax.swing.JDialog {

    private final String _link;

    private boolean _download;

    private final List<HashMap> _download_links;

    private long _total_space;

    private int _mega_error;

    private volatile boolean working = false;

    private volatile boolean exit = false;

    public List<HashMap> getDownload_links() {
        return Collections.unmodifiableList(_download_links);
    }

    public boolean isDownload() {
        return _download;
    }

    public int isMega_error() {
        return _mega_error;
    }

    /**
     * Creates new form FolderLink
     *
     * @param parent
     * @param link
     */
    public FolderLinkDialog(MainPanelView parent, boolean modal, String link) {

        super(parent, modal);

        _mega_error = 0;
        _total_space = 0L;
        _download = false;
        _download_links = new ArrayList<>();
        _link = link;

        MiscTools.GUIRunAndWait(() -> {

            initComponents();

            updateFonts(this, GUI_FONT, parent.getMain_panel().getZoom_factor());

            translateLabels(this);

            node_bar.setIndeterminate(true);

            folder_link_label.setText(link);

            restore_button.setVisible(false);

            final Dialog tthis = this;

            THREAD_POOL.execute(() -> {
                _loadMegaDirTree();

                if (_mega_error == 0) {

                    _genDownloadLiks();

                    MiscTools.GUIRun(() -> {

                        dance_button.setText(LabelTranslatorSingleton.getInstance().translate("Let's dance, baby"));

                        pack();
                    });

                } else if (_mega_error == -18) {

                    MiscTools.GUIRun(() -> {
                        JOptionPane.showMessageDialog(tthis, LabelTranslatorSingleton.getInstance().translate("MEGA LINK TEMPORARILY UNAVAILABLE!"), "Error", JOptionPane.ERROR_MESSAGE);

                        setVisible(false);
                    });

                } else {

                    MiscTools.GUIRun(() -> {
                        JOptionPane.showMessageDialog(tthis, LabelTranslatorSingleton.getInstance().translate("MEGA LINK ERROR!"), "Error", JOptionPane.ERROR_MESSAGE);

                        setVisible(false);
                    });
                }
            });

            pack();

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        file_tree_scrollpane = new javax.swing.JScrollPane();
        skip_button = new javax.swing.JButton();
        link_detected_label = new javax.swing.JLabel();
        dance_button = new javax.swing.JButton();
        folder_link_label = new javax.swing.JLabel();
        warning_label = new javax.swing.JLabel();
        skip_rest_button = new javax.swing.JButton();
        restore_button = new javax.swing.JButton();
        total_space_label = new javax.swing.JLabel();
        node_bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FolderLink");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        file_tree.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        file_tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        file_tree.setDoubleBuffered(true);
        file_tree.setEnabled(false);
        file_tree_scrollpane.setViewportView(file_tree);

        skip_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        skip_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-trash-can-30.png"))); // NOI18N
        skip_button.setText("REMOVE THIS");
        skip_button.setDoubleBuffered(true);
        skip_button.setEnabled(false);
        skip_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skip_buttonActionPerformed(evt);
            }
        });

        link_detected_label.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        link_detected_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-folder-30.png"))); // NOI18N
        link_detected_label.setText("Folder link detected!");
        link_detected_label.setDoubleBuffered(true);

        dance_button.setBackground(new java.awt.Color(102, 204, 255));
        dance_button.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        dance_button.setForeground(new java.awt.Color(255, 255, 255));
        dance_button.setText("Loading...");
        dance_button.setDoubleBuffered(true);
        dance_button.setEnabled(false);
        dance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dance_buttonActionPerformed(evt);
            }
        });

        folder_link_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        folder_link_label.setText("jLabel2");
        folder_link_label.setDoubleBuffered(true);

        warning_label.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        warning_label.setText("If you DO NOT want to transfer some folder or file you can REMOVE it (to select several items at the same time use CTRL + LMOUSE).");
        warning_label.setDoubleBuffered(true);
        warning_label.setEnabled(false);

        skip_rest_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        skip_rest_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-trash-can-30.png"))); // NOI18N
        skip_rest_button.setText("REMOVE ALL EXCEPT THIS");
        skip_rest_button.setDoubleBuffered(true);
        skip_rest_button.setEnabled(false);
        skip_rest_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skip_rest_buttonActionPerformed(evt);
            }
        });

        restore_button.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        restore_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-undelete-30.png"))); // NOI18N
        restore_button.setText("Restore folder data");
        restore_button.setDoubleBuffered(true);
        restore_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restore_buttonActionPerformed(evt);
            }
        });

        total_space_label.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        total_space_label.setText("[0 B]");
        total_space_label.setDoubleBuffered(true);
        total_space_label.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(node_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(link_detected_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(file_tree_scrollpane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total_space_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(warning_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(skip_rest_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(skip_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dance_button))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(folder_link_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(restore_button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(link_detected_label)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folder_link_label)
                    .addComponent(restore_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(file_tree_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(node_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(skip_rest_button)
                        .addComponent(skip_button)
                        .addComponent(dance_button))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(total_space_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(warning_label)
                        .addGap(49, 49, 49)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void skip_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skip_buttonActionPerformed

        if (deleteSelectedTreeItems(file_tree)) {
            file_tree.setEnabled(false);
            node_bar.setVisible(true);
            skip_rest_button.setEnabled(false);
            skip_button.setEnabled(false);
            THREAD_POOL.execute(() -> {
                MiscTools.resetTreeFolderSizes(((MegaMutableTreeNode) file_tree.getModel().getRoot()));
                MiscTools.calculateTreeFolderSizes(((MegaMutableTreeNode) file_tree.getModel().getRoot()));
                _genDownloadLiks();
                MiscTools.GUIRun(() -> {
                    restore_button.setVisible(true);

                    file_tree.setEnabled(true);

                    file_tree.setModel(new DefaultTreeModel((TreeNode) file_tree.getModel().getRoot()));

                    boolean root_childs = ((TreeNode) file_tree.getModel().getRoot()).getChildCount() > 0;

                    dance_button.setEnabled(root_childs);

                    skip_button.setEnabled(root_childs);

                    skip_rest_button.setEnabled(root_childs);
                });
            });

        }

    }//GEN-LAST:event_skip_buttonActionPerformed

    private void dance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dance_buttonActionPerformed

        _download = true;

        dispose();
    }//GEN-LAST:event_dance_buttonActionPerformed

    private void skip_rest_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skip_rest_buttonActionPerformed

        if (deleteAllExceptSelectedTreeItems(file_tree)) {
            file_tree.setEnabled(false);
            node_bar.setVisible(true);
            skip_rest_button.setEnabled(false);
            skip_button.setEnabled(false);
            THREAD_POOL.execute(() -> {
                MiscTools.resetTreeFolderSizes(((MegaMutableTreeNode) file_tree.getModel().getRoot()));
                MiscTools.calculateTreeFolderSizes(((MegaMutableTreeNode) file_tree.getModel().getRoot()));

                _genDownloadLiks();
                MiscTools.GUIRun(() -> {
                    restore_button.setVisible(true);

                    file_tree.setEnabled(true);

                    file_tree.setModel(new DefaultTreeModel((TreeNode) file_tree.getModel().getRoot()));

                    boolean root_childs = ((TreeNode) file_tree.getModel().getRoot()).getChildCount() > 0;

                    dance_button.setEnabled(root_childs);

                    skip_button.setEnabled(root_childs);

                    skip_rest_button.setEnabled(root_childs);
                });
            });

        }
    }//GEN-LAST:event_skip_rest_buttonActionPerformed

    private void restore_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restore_buttonActionPerformed

        restore_button.setText(LabelTranslatorSingleton.getInstance().translate("Restoring data, please wait..."));

        file_tree.setEnabled(false);

        restore_button.setEnabled(false);

        dance_button.setEnabled(false);

        node_bar.setVisible(true);

        node_bar.setIndeterminate(true);

        skip_button.setEnabled(false);

        skip_rest_button.setEnabled(false);

        THREAD_POOL.execute(() -> {
            _loadMegaDirTree();
            _genDownloadLiks();
            MiscTools.GUIRun(() -> {
                restore_button.setVisible(false);
                restore_button.setText(LabelTranslatorSingleton.getInstance().translate("Restore folder data"));
                boolean root_childs = ((TreeNode) file_tree.getModel().getRoot()).getChildCount() > 0;

                for (JComponent c : new JComponent[]{restore_button, dance_button, skip_button, skip_rest_button, file_tree}) {

                    c.setEnabled(root_childs);
                }

                skip_button.setEnabled(root_childs);

                skip_rest_button.setEnabled(root_childs);
            });
        });

    }//GEN-LAST:event_restore_buttonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        if (working && JOptionPane.showConfirmDialog(this, "EXIT?") == 0) {
            dispose();
            exit = true;
        } else if (!working) {
            dispose();
            exit = true;
        }
    }//GEN-LAST:event_formWindowClosing

    private int _loadMegaDirTree() {

        try {

            working = true;

            HashMap<String, Object> folder_nodes;

            MegaAPI ma = new MegaAPI();

            String folder_id = findFirstRegex("#F!([^!]+)", _link, 1);

            String folder_key = findFirstRegex("#F![^!]+!(.+)", _link, 1);

            folder_nodes = ma.getFolderNodes(folder_id, folder_key, node_bar);

            MegaMutableTreeNode root = null;

            final int nodos_totales = folder_nodes.size();

            MiscTools.GUIRun(() -> {
                node_bar.setIndeterminate(false);
                node_bar.setMaximum(nodos_totales);
                node_bar.setValue(0);
            });

            int conta_nodo = 0;

            for (Object o : folder_nodes.values()) {

                if (exit) {
                    return 1;
                }

                conta_nodo++;

                int c = conta_nodo;
                MiscTools.GUIRun(() -> {

                    node_bar.setValue(c);
                });

                HashMap<String, Object> current_hashmap_node = (HashMap<String, Object>) o;

                MegaMutableTreeNode current_node;

                if (current_hashmap_node.get("jtree_node") == null) {

                    current_node = new MegaMutableTreeNode(current_hashmap_node);

                    current_hashmap_node.put("jtree_node", current_node);

                } else {

                    current_node = (MegaMutableTreeNode) current_hashmap_node.get("jtree_node");
                }

                String parent_id = (String) current_hashmap_node.get("parent");

                root = null;

                do {

                    if (folder_nodes.get(parent_id) != null) {

                        HashMap<String, Object> parent_hashmap_node = (HashMap) folder_nodes.get(parent_id);

                        MegaMutableTreeNode parent_node;

                        if (parent_hashmap_node.get("jtree_node") == null) {

                            parent_node = new MegaMutableTreeNode(parent_hashmap_node);

                            parent_hashmap_node.put("jtree_node", parent_node);

                        } else {

                            parent_node = (MegaMutableTreeNode) parent_hashmap_node.get("jtree_node");
                        }

                        parent_node.add(current_node);

                        parent_id = (String) parent_hashmap_node.get("parent");

                        current_node = parent_node;

                    } else {

                        root = current_node;
                    }

                } while (current_node != root);
            }

            MiscTools.GUIRun(() -> {

                node_bar.setIndeterminate(true);
            });

            MiscTools.sortTree(root);

            MiscTools.calculateTreeFolderSizes(root);

            if (root == null) {
                LOG.log(SEVERE, null, "MEGA FOLDER ERROR (EMPTY?)");

                _mega_error = 2;

            } else {
                final JTree ftree = file_tree;

                final MegaMutableTreeNode roott = root;

                MiscTools.GUIRun(() -> {

                    node_bar.setIndeterminate(true);

                    ftree.setModel(new DefaultTreeModel(roott));

                    ftree.setRootVisible(roott != null ? roott.getChildCount() > 0 : false);

                    ftree.setEnabled(true);
                });

            }

        } catch (MegaAPIException mex) {

            LOG.log(SEVERE, null, mex);

            _mega_error = mex.getCode();

        } catch (Exception ex) {

            LOG.log(SEVERE, null, ex);

            _mega_error = 1;
        }

        working = false;

        return 0;

    }

    private void _genDownloadLiks() {

        MiscTools.GUIRun(() -> {
            working = true;

            String folder_id = findFirstRegex("#F!([^!]+)", _link, 1);

            _download_links.clear();

            MegaMutableTreeNode root = (MegaMutableTreeNode) file_tree.getModel().getRoot();

            Enumeration files_tree = root.depthFirstEnumeration();

            THREAD_POOL.execute(() -> {

                _total_space = 0L;

                while (files_tree.hasMoreElements()) {

                    MegaMutableTreeNode node = (MegaMutableTreeNode) files_tree.nextElement();

                    if (node.isLeaf() && node != root && ((HashMap<String, Object>) node.getUserObject()).get("size") != null) {

                        String path = "";

                        Object[] object_path = node.getUserObjectPath();

                        for (Object p : object_path) {

                            path += File.separator + ((Map<String, Object>) p).get("name");
                        }

                        path = path.replaceAll("^/+", "").replaceAll("^\\+", "").trim();

                        String url = "https://mega.nz/#N!" + ((Map<String, Object>) node.getUserObject()).get("h") + "!" + ((Map<String, Object>) node.getUserObject()).get("key") + "###n=" + folder_id;

                        HashMap<String, Object> download_link = new HashMap<>();

                        download_link.put("url", url);

                        download_link.put("filename", cleanFilePath(path));

                        download_link.put("filekey", ((Map<String, Object>) node.getUserObject()).get("key"));

                        download_link.put("filesize", ((Map<String, Object>) node.getUserObject()).get("size"));

                        _total_space += (long) download_link.get("filesize");

                        _download_links.add(download_link);
                    } else if (node.isLeaf() && node != root) {
                        String path = "";

                        Object[] object_path = node.getUserObjectPath();

                        for (Object p : object_path) {

                            path += File.separator + ((Map<String, Object>) p).get("name");
                        }

                        path = path.replaceAll("^/+", "").replaceAll("^\\+", "").trim();

                        HashMap<String, Object> download_link = new HashMap<>();

                        download_link.put("url", "*");

                        download_link.put("filename", cleanFilePath(path));

                        download_link.put("type", ((HashMap<String, Object>) node.getUserObject()).get("type"));

                        _download_links.add(download_link);
                    }
                }

                MiscTools.GUIRun(() -> {

                    total_space_label.setText("[" + formatBytes(_total_space) + "]");

                    for (JComponent c : new JComponent[]{dance_button, warning_label, skip_button, skip_rest_button, total_space_label}) {

                        c.setEnabled(root.getChildCount() > 0);
                    }

                    node_bar.setVisible(false);

                    working = false;
                });

            });
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dance_button;
    private final javax.swing.JTree file_tree = new javax.swing.JTree();
    private javax.swing.JScrollPane file_tree_scrollpane;
    private javax.swing.JLabel folder_link_label;
    private javax.swing.JLabel link_detected_label;
    private javax.swing.JProgressBar node_bar;
    private javax.swing.JButton restore_button;
    private javax.swing.JButton skip_button;
    private javax.swing.JButton skip_rest_button;
    private javax.swing.JLabel total_space_label;
    private javax.swing.JLabel warning_label;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(FolderLinkDialog.class.getName());
}
