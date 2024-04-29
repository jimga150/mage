package org.mage.test.cards.single.rex;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class IndominusRexAlphaTests extends CardTestPlayerBase {

    @Test
    public void testIndominusRexAlpha() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1); // Flying
        addCard(Zone.HAND, playerA, "Rograkh, Son of Rohgahh", 1); // First strike, menace, trample, Partner
        addCard(Zone.HAND, playerA, "Adorned Pouncer", 1); // Double strike, Eternalize
        addCard(Zone.HAND, playerA, "Ankle Biter", 1); // Deathtouch
        addCard(Zone.HAND, playerA, "Gladecover Scout", 1); // Hexproof
        addCard(Zone.HAND, playerA, "Banehound", 1); // Lifelink, haste
        addCard(Zone.HAND, playerA, "Bontu the Glorified", 1); // Menace, indestructible
        addCard(Zone.HAND, playerA, "Aerial Responder", 1); // Flying, vigilance, lifelink
        addCard(Zone.HAND, playerA, "Stonecoil Serpent", 1); // Reach, trample, protection from multicolored
        addCard(Zone.HAND, playerA, "Codespell Cleric", 1); // Vigilance

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Ornithopter^Rograkh, Son of Rohgahh^Adorned Pouncer^Ankle Biter^Gladecover Scout" +
                "^Banehound^Bontu the Glorified^Aerial Responder^Stonecoil Serpent^Codespell Cleric");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FIRST_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DOUBLE_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HEXPROOF, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.INDESTRUCTIBLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.MENACE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.REACH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.TRAMPLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.VIGILANCE, 1);

        assertHandCount(playerA, 12);

    }

    @Test
    public void testIndominusRexAlphaSubset() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1); // Flying
        addCard(Zone.HAND, playerA, "Rograkh, Son of Rohgahh", 1); // First strike, menace, trample, Partner
        addCard(Zone.HAND, playerA, "Adorned Pouncer", 1); // Double strike, Eternalize
        addCard(Zone.HAND, playerA, "Ankle Biter", 1); // Deathtouch
        addCard(Zone.HAND, playerA, "Banehound", 1); // Lifelink, haste
        addCard(Zone.HAND, playerA, "Bontu the Glorified", 1); // Menace, indestructible
        addCard(Zone.HAND, playerA, "Aerial Responder", 1); // Flying, vigilance, lifelink
        addCard(Zone.HAND, playerA, "Codespell Cleric", 1); // Vigilance

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Ornithopter^Rograkh, Son of Rohgahh^Adorned Pouncer^Ankle Biter" +
                "^Banehound^Bontu the Glorified^Aerial Responder^Codespell Cleric");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FIRST_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DOUBLE_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.INDESTRUCTIBLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.MENACE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.TRAMPLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.VIGILANCE, 1);

        assertHandCount(playerA, 10);

    }

}
