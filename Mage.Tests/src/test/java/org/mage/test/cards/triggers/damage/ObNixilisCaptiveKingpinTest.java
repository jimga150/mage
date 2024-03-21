package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class ObNixilisCaptiveKingpinTest extends CardTestCommander4Players {

    @Test
    public void damageController1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Memnite");

        attack(2, playerD, "Memnite", playerA);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

    @Test
    public void damage1Opp1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Memnite", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 1);
    }

    @Test
    public void damage1Opp2Points() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Expedition Envoy");

        attack(1, playerA, "Expedition Envoy", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

    @Test
    public void damage2Opp1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Memnite", playerC);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 1);
    }

    @Test
    public void damage2Opp2Points() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Expedition Envoy", 2);

        attack(1, playerA, "Expedition Envoy", playerB);
        attack(1, playerA, "Expedition Envoy", playerC);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

    @Test
    public void payLife1Opp1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Arid Mesa");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Pay 1 life");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 1);
    }

    @Test
    public void payLife1Opp2Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        // {2}, Pay 2 life: Draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Book of Rass");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Pay 2 life");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

    @Test
    public void loseLife1Opp1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // {1}{B}, {T}: Target player loses 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Acolyte of Xathrid");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}, {T}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 1);
    }

    @Test
    public void loseLife1Opp2Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Target player draws two cards and loses 2 life.
        addCard(Zone.HAND, playerA, "Blood Pact");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Pact");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

    @Test
    public void loseLifeAll1Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // {2}{W}: Target player gains 1 life.
        // {2}{B}: Each player loses 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Orzhov Guildmage");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 1);
    }

    @Test
    public void loseLifeAll2Point() {
        addCard(Zone.BATTLEFIELD, playerA, "Ob Nixilis, Captive Kingpin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // Each player loses 2 life. You draw two cards.
        addCard(Zone.HAND, playerA, "Crushing Disappointment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crushing Disappointment");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount("Ob Nixilis, Captive Kingpin", CounterType.P1P1, 0);
    }

}

