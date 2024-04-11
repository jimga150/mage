package mage.client.deckeditor;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.mock.MockCard;
import mage.cards.mock.MockSplitCard;
import mage.client.components.LegalityLabel;
import mage.deck.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 * @author Elandril
 */
public class DeckLegalityPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(DeckLegalityPanel.class);

    /**
     * Creates new form DeckLegalityPanel
     */
    public DeckLegalityPanel() {
        initComponents();
        initDeckLabels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        previewUnknown = new javax.swing.JLabel();
        previewLegal = new javax.swing.JLabel();
        previewNotLegal = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(85, 35));
        setName("DeckLegalityPanel"); // NOI18N
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        previewUnknown.setBackground(new java.awt.Color(174, 174, 174));
        previewUnknown.setForeground(new java.awt.Color(255, 255, 255));
        previewUnknown.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewUnknown.setText("Unknown");
        previewUnknown.setMaximumSize(new java.awt.Dimension(150, 50));
        previewUnknown.setMinimumSize(new java.awt.Dimension(75, 25));
        previewUnknown.setName("previewUnknown"); // NOI18N
        previewUnknown.setOpaque(true);
        previewUnknown.setPreferredSize(new java.awt.Dimension(75, 25));
        add(previewUnknown);

        previewLegal.setBackground(new java.awt.Color(117, 152, 110));
        previewLegal.setForeground(new java.awt.Color(255, 255, 255));
        previewLegal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewLegal.setText("Legal");
        previewLegal.setMaximumSize(new java.awt.Dimension(150, 50));
        previewLegal.setMinimumSize(new java.awt.Dimension(75, 25));
        previewLegal.setName("previewLegal"); // NOI18N
        previewLegal.setOpaque(true);
        previewLegal.setPreferredSize(new java.awt.Dimension(75, 25));
        add(previewLegal);

        previewNotLegal.setBackground(new java.awt.Color(191, 84, 74));
        previewNotLegal.setForeground(new java.awt.Color(255, 255, 255));
        previewNotLegal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewNotLegal.setText("Not Legal");
        previewNotLegal.setMaximumSize(new java.awt.Dimension(150, 50));
        previewNotLegal.setMinimumSize(new java.awt.Dimension(75, 25));
        previewNotLegal.setName("previewNotLegal"); // NOI18N
        previewNotLegal.setOpaque(true);
        previewNotLegal.setPreferredSize(new java.awt.Dimension(75, 25));
        add(previewNotLegal);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel previewLegal;
    private javax.swing.JLabel previewNotLegal;
    private javax.swing.JLabel previewUnknown;
    // End of variables declaration//GEN-END:variables

    private void initDeckLabels() {
        remove(previewUnknown);
        remove(previewLegal);
        remove(previewNotLegal);

        Stream.of(
                // most popular
                new Standard(), new Pioneer(), new Modern(), new Legacy(), new Vintage(), new Pauper(), new Historic(),
                // common
                new Commander(), new Oathbreaker(), new Brawl(),
                // other
                new Frontier(), new HistoricalType2(), new PennyDreadfulCommander(), new EuropeanHighlander(), new CanadianHighlander()
                // not used: new Eternal(), new Momir(), new TinyLeaders()
        ).forEach(this::addLegalityLabel);
        addHidePanelButton();

        revalidate();
        repaint();
    }

    protected LegalityLabel addLegalityLabel(DeckValidator validator) {
        LegalityLabel label = new LegalityLabel(validator);
        add(label);
        return label;
    }

    protected void addHidePanelButton() {
        JButton button = LegalityLabel.createHideButton();
        button.addActionListener(e -> this.setVisible(false));
        add(button);
    }

    private boolean isMockCard(Card card) {
        return card instanceof MockCard || card instanceof MockSplitCard;
    }

    public void validateDeck(Deck deck) {
        // Non game GUI like Deck Editor works with Mock cards (fake cards with texts only), but validate must use
        // real cards all the time. So convert it here before check.

        final Deck deckToValidate;
        if (deck.getCards().stream().noneMatch(this::isMockCard)
                && deck.getSideboard().stream().noneMatch(this::isMockCard)) {
            // contains real cards, e.g. it's a game
            deckToValidate = deck;
        } else {
            // contains mock cards, e.g. it's a Deck Editor
            try {
                deckToValidate = Deck.load(deck.prepareCardsOnlyDeck(), true, false);
            } catch (Exception ex) {
                logger.error("Can't load real deck cards for validate: " + ex.getMessage(), ex);
                return;
            }
        }

        Arrays.stream(getComponents())
                .filter(LegalityLabel.class::isInstance)
                .map(LegalityLabel.class::cast)
                .forEach(label -> label.validateDeck(deckToValidate));
    }

}
