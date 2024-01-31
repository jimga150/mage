package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MurdersAtKarlovManorCommander extends ExpansionSet {

    private static final MurdersAtKarlovManorCommander instance = new MurdersAtKarlovManorCommander();

    public static MurdersAtKarlovManorCommander getInstance() {
        return instance;
    }

    private MurdersAtKarlovManorCommander() {
        super("Murders at Karlov Manor Commander", "MKC", ExpansionSet.buildDate(2024, 2, 9), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Academy Manufactor", 221, Rarity.RARE, mage.cards.a.AcademyManufactor.class));
        cards.add(new SetCardInfo("Access Tunnel", 247, Rarity.UNCOMMON, mage.cards.a.AccessTunnel.class));
        cards.add(new SetCardInfo("Adrix and Nev, Twincasters", 198, Rarity.MYTHIC, mage.cards.a.AdrixAndNevTwincasters.class));
        cards.add(new SetCardInfo("Aerial Extortionist", 54, Rarity.RARE, mage.cards.a.AerialExtortionist.class));
        cards.add(new SetCardInfo("Agitator Ant", 145, Rarity.RARE, mage.cards.a.AgitatorAnt.class));
        cards.add(new SetCardInfo("Ainok Survivalist", 165, Rarity.UNCOMMON, mage.cards.a.AinokSurvivalist.class));
        cards.add(new SetCardInfo("Akroma, Angel of Fury", 146, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Alandra, Sky Dreamer", 94, Rarity.RARE, mage.cards.a.AlandraSkyDreamer.class));
        cards.add(new SetCardInfo("Amphin Mutineer", 95, Rarity.RARE, mage.cards.a.AmphinMutineer.class));
        cards.add(new SetCardInfo("Ancient Stone Idol", 222, Rarity.RARE, mage.cards.a.AncientStoneIdol.class));
        cards.add(new SetCardInfo("Angel of the Ruins", 55, Rarity.RARE, mage.cards.a.AngelOfTheRuins.class));
        cards.add(new SetCardInfo("Animate Dead", 125, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Anya, Merciless Angel", 199, Rarity.MYTHIC, mage.cards.a.AnyaMercilessAngel.class));
        cards.add(new SetCardInfo("Arcane Signet", 223, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Ash Barrens", 248, Rarity.UNCOMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Ashcloud Phoenix", 147, Rarity.MYTHIC, mage.cards.a.AshcloudPhoenix.class));
        cards.add(new SetCardInfo("Austere Command", 56, Rarity.RARE, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Azorius Chancery", 249, Rarity.COMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Azorius Signet", 224, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Baleful Strix", 200, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Beast Whisperer", 166, Rarity.RARE, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Bennie Bracks, Zoologist", 57, Rarity.MYTHIC, mage.cards.b.BennieBracksZoologist.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 126, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Bloodthirsty Blade", 225, Rarity.UNCOMMON, mage.cards.b.BloodthirstyBlade.class));
        cards.add(new SetCardInfo("Bojuka Bog", 250, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Boros Garrison", 251, Rarity.UNCOMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Reckoner", 201, Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Brainstorm", 96, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brash Taunter", 148, Rarity.RARE, mage.cards.b.BrashTaunter.class));
        cards.add(new SetCardInfo("Broodhatch Nantuko", 167, Rarity.UNCOMMON, mage.cards.b.BroodhatchNantuko.class));
        cards.add(new SetCardInfo("Canopy Vista", 252, Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Castle Ardenvale", 253, Rarity.RARE, mage.cards.c.CastleArdenvale.class));
        cards.add(new SetCardInfo("Chaos Warp", 149, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Choked Estuary", 254, Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Chulane, Teller of Tales", 202, Rarity.MYTHIC, mage.cards.c.ChulaneTellerOfTales.class));
        cards.add(new SetCardInfo("Cinder Glade", 255, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Comeuppance", 58, Rarity.RARE, mage.cards.c.Comeuppance.class));
        cards.add(new SetCardInfo("Command Tower", 256, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Confirm Suspicions", 97, Rarity.RARE, mage.cards.c.ConfirmSuspicions.class));
        cards.add(new SetCardInfo("Connive // Concoct", 203, Rarity.RARE, mage.cards.c.ConniveConcoct.class));
        cards.add(new SetCardInfo("Consider", 98, Rarity.COMMON, mage.cards.c.Consider.class));
        cards.add(new SetCardInfo("Curate", 99, Rarity.COMMON, mage.cards.c.Curate.class));
        cards.add(new SetCardInfo("Curse of Opulence", 150, Rarity.UNCOMMON, mage.cards.c.CurseOfOpulence.class));
        cards.add(new SetCardInfo("Darien, King of Kjeldor", 59, Rarity.RARE, mage.cards.d.DarienKingOfKjeldor.class));
        cards.add(new SetCardInfo("Darkwater Catacombs", 257, Rarity.RARE, mage.cards.d.DarkwaterCatacombs.class));
        cards.add(new SetCardInfo("Deathmist Raptor", 168, Rarity.MYTHIC, mage.cards.d.DeathmistRaptor.class));
        cards.add(new SetCardInfo("Decimate", 204, Rarity.RARE, mage.cards.d.Decimate.class));
        cards.add(new SetCardInfo("Deep Analysis", 100, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Deflecting Palm", 205, Rarity.RARE, mage.cards.d.DeflectingPalm.class));
        cards.add(new SetCardInfo("Den Protector", 169, Rarity.RARE, mage.cards.d.DenProtector.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 258, Rarity.UNCOMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Dimir Signet", 226, Rarity.UNCOMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Dimir Spybug", 206, Rarity.UNCOMMON, mage.cards.d.DimirSpybug.class));
        cards.add(new SetCardInfo("Discovery // Dispersal", 207, Rarity.UNCOMMON, mage.cards.d.DiscoveryDispersal.class));
        cards.add(new SetCardInfo("Disinformation Campaign", 208, Rarity.UNCOMMON, mage.cards.d.DisinformationCampaign.class));
        cards.add(new SetCardInfo("Disorder in the Court", 209, Rarity.RARE, mage.cards.d.DisorderInTheCourt.class));
        cards.add(new SetCardInfo("Disrupt Decorum", 151, Rarity.RARE, mage.cards.d.DisruptDecorum.class));
        cards.add(new SetCardInfo("Dogged Detective", 127, Rarity.RARE, mage.cards.d.DoggedDetective.class));
        cards.add(new SetCardInfo("Doom Whisperer", 128, Rarity.MYTHIC, mage.cards.d.DoomWhisperer.class));
        cards.add(new SetCardInfo("Dream Eater", 101, Rarity.MYTHIC, mage.cards.d.DreamEater.class));
        cards.add(new SetCardInfo("Drownyard Temple", 259, Rarity.RARE, mage.cards.d.DrownyardTemple.class));
        cards.add(new SetCardInfo("Duelist's Heritage", 60, Rarity.RARE, mage.cards.d.DuelistsHeritage.class));
        cards.add(new SetCardInfo("Dusk // Dawn", 61, Rarity.RARE, mage.cards.d.DuskDawn.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 62, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Enhanced Surveillance", 102, Rarity.UNCOMMON, mage.cards.e.EnhancedSurveillance.class));
        cards.add(new SetCardInfo("Ephara's Dispersal", 103, Rarity.COMMON, mage.cards.e.EpharasDispersal.class));
        cards.add(new SetCardInfo("Erdwal Illuminator", 104, Rarity.UNCOMMON, mage.cards.e.ErdwalIlluminator.class));
        cards.add(new SetCardInfo("Esix, Fractal Bloom", 210, Rarity.MYTHIC, mage.cards.e.EsixFractalBloom.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 152, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Ethereal Investigator", 105, Rarity.RARE, mage.cards.e.EtherealInvestigator.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 227, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Exalted Angel", 63, Rarity.RARE, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Exotic Orchard", 260, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Farewell", 64, Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Fell the Mighty", 65, Rarity.RARE, mage.cards.f.FellTheMighty.class));
        cards.add(new SetCardInfo("Fellwar Stone", 228, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fetid Pools", 261, Rarity.RARE, mage.cards.f.FetidPools.class));
        cards.add(new SetCardInfo("Fiendish Duo", 153, Rarity.MYTHIC, mage.cards.f.FiendishDuo.class));
        cards.add(new SetCardInfo("Finale of Revelation", 106, Rarity.MYTHIC, mage.cards.f.FinaleOfRevelation.class));
        cards.add(new SetCardInfo("Fortified Village", 262, Rarity.RARE, mage.cards.f.FortifiedVillage.class));
        cards.add(new SetCardInfo("Frontier Warmonger", 154, Rarity.RARE, mage.cards.f.FrontierWarmonger.class));
        cards.add(new SetCardInfo("Fumigate", 66, Rarity.RARE, mage.cards.f.Fumigate.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 263, Rarity.RARE, mage.cards.f.FurycalmSnarl.class));
        cards.add(new SetCardInfo("Game Trail", 264, Rarity.RARE, mage.cards.g.GameTrail.class));
        cards.add(new SetCardInfo("Ghostly Prison", 67, Rarity.UNCOMMON, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Gideon's Sacrifice", 68, Rarity.COMMON, mage.cards.g.GideonsSacrifice.class));
        cards.add(new SetCardInfo("Gisela, Blade of Goldnight", 211, Rarity.MYTHIC, mage.cards.g.GiselaBladeOfGoldnight.class));
        cards.add(new SetCardInfo("Graf Mole", 170, Rarity.UNCOMMON, mage.cards.g.GrafMole.class));
        cards.add(new SetCardInfo("Grave Titan", 129, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Gruul Turf", 265, Rarity.UNCOMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Hidden Dragonslayer", 69, Rarity.RARE, mage.cards.h.HiddenDragonslayer.class));
        cards.add(new SetCardInfo("Hooded Hydra", 171, Rarity.MYTHIC, mage.cards.h.HoodedHydra.class));
        cards.add(new SetCardInfo("Hornet Queen", 172, Rarity.RARE, mage.cards.h.HornetQueen.class));
        cards.add(new SetCardInfo("Hostile Desert", 266, Rarity.RARE, mage.cards.h.HostileDesert.class));
        cards.add(new SetCardInfo("Hydroid Krasis", 212, Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class));
        cards.add(new SetCardInfo("Idol of Oblivion", 229, Rarity.RARE, mage.cards.i.IdolOfOblivion.class));
        cards.add(new SetCardInfo("Imperial Hellkite", 155, Rarity.RARE, mage.cards.i.ImperialHellkite.class));
        cards.add(new SetCardInfo("Inspiring Statuary", 230, Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Irrigated Farmland", 267, Rarity.RARE, mage.cards.i.IrrigatedFarmland.class));
        cards.add(new SetCardInfo("Jeska's Will", 156, Rarity.RARE, mage.cards.j.JeskasWill.class));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 173, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class));
        cards.add(new SetCardInfo("Jungle Shrine", 268, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Junk Winder", 107, Rarity.UNCOMMON, mage.cards.j.JunkWinder.class));
        cards.add(new SetCardInfo("Kappa Cannoneer", 108, Rarity.RARE, mage.cards.k.KappaCannoneer.class));
        cards.add(new SetCardInfo("Kazuul, Tyrant of the Cliffs", 157, Rarity.UNCOMMON, mage.cards.k.KazuulTyrantOfTheCliffs.class));
        cards.add(new SetCardInfo("Keeper of the Accord", 70, Rarity.RARE, mage.cards.k.KeeperOfTheAccord.class));
        cards.add(new SetCardInfo("Kessig Wolf Run", 269, Rarity.RARE, mage.cards.k.KessigWolfRun.class));
        cards.add(new SetCardInfo("Kher Keep", 270, Rarity.RARE, mage.cards.k.KherKeep.class));
        cards.add(new SetCardInfo("Killer Service", 174, Rarity.RARE, mage.cards.k.KillerService.class));
        cards.add(new SetCardInfo("Koma, Cosmos Serpent", 213, Rarity.MYTHIC, mage.cards.k.KomaCosmosSerpent.class));
        cards.add(new SetCardInfo("Krosan Cloudscraper", 175, Rarity.RARE, mage.cards.k.KrosanCloudscraper.class));
        cards.add(new SetCardInfo("Krosan Colossus", 176, Rarity.UNCOMMON, mage.cards.k.KrosanColossus.class));
        cards.add(new SetCardInfo("Krosan Verge", 271, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Labyrinth of Skophos", 272, Rarity.RARE, mage.cards.l.LabyrinthOfSkophos.class));
        cards.add(new SetCardInfo("Lazav, the Multifarious", 214, Rarity.RARE, mage.cards.l.LazavTheMultifarious.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 231, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 273, Rarity.UNCOMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Lonis, Cryptozoologist", 215, Rarity.RARE, mage.cards.l.LonisCryptozoologist.class));
        cards.add(new SetCardInfo("Loran of the Third Path", 71, Rarity.RARE, mage.cards.l.LoranOfTheThirdPath.class));
        cards.add(new SetCardInfo("Martial Impetus", 72, Rarity.UNCOMMON, mage.cards.m.MartialImpetus.class));
        cards.add(new SetCardInfo("Massacre Wurm", 130, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class));
        cards.add(new SetCardInfo("Master of Death", 216, Rarity.RARE, mage.cards.m.MasterOfDeath.class));
        cards.add(new SetCardInfo("Master of Pearls", 73, Rarity.RARE, mage.cards.m.MasterOfPearls.class));
        cards.add(new SetCardInfo("Mastery of the Unseen", 74, Rarity.RARE, mage.cards.m.MasteryOfTheUnseen.class));
        cards.add(new SetCardInfo("Mechanized Production", 109, Rarity.MYTHIC, mage.cards.m.MechanizedProduction.class));
        cards.add(new SetCardInfo("Mind Stone", 232, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mirror Entity", 75, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Mission Briefing", 110, Rarity.RARE, mage.cards.m.MissionBriefing.class));
        cards.add(new SetCardInfo("Morska, Undersea Sleuth", 3, Rarity.MYTHIC, mage.cards.m.MorskaUnderseaSleuth.class));
        cards.add(new SetCardInfo("Mossfire Valley", 274, Rarity.RARE, mage.cards.m.MossfireValley.class));
        cards.add(new SetCardInfo("Mosswort Bridge", 275, Rarity.RARE, mage.cards.m.MosswortBridge.class));
        cards.add(new SetCardInfo("Mulldrifter", 111, Rarity.UNCOMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Myriad Landscape", 276, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Mystic Sanctuary", 277, Rarity.COMMON, mage.cards.m.MysticSanctuary.class));
        cards.add(new SetCardInfo("Nadir Kraken", 112, Rarity.RARE, mage.cards.n.NadirKraken.class));
        cards.add(new SetCardInfo("Nantuko Vigilante", 177, Rarity.COMMON, mage.cards.n.NantukoVigilante.class));
        cards.add(new SetCardInfo("Nature's Lore", 178, Rarity.UNCOMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Necromancy", 131, Rarity.UNCOMMON, mage.cards.n.Necromancy.class));
        cards.add(new SetCardInfo("Needle Spires", 278, Rarity.RARE, mage.cards.n.NeedleSpires.class));
        cards.add(new SetCardInfo("Neheb, the Eternal", 158, Rarity.MYTHIC, mage.cards.n.NehebTheEternal.class));
        cards.add(new SetCardInfo("Nettlecyst", 233, Rarity.RARE, mage.cards.n.Nettlecyst.class));
        cards.add(new SetCardInfo("Nightveil Sprite", 113, Rarity.UNCOMMON, mage.cards.n.NightveilSprite.class));
        cards.add(new SetCardInfo("Notion Rain", 217, Rarity.COMMON, mage.cards.n.NotionRain.class));
        cards.add(new SetCardInfo("Obscuring Aether", 179, Rarity.RARE, mage.cards.o.ObscuringAether.class));
        cards.add(new SetCardInfo("Ohran Frostfang", 180, Rarity.RARE, mage.cards.o.OhranFrostfang.class));
        cards.add(new SetCardInfo("Ongoing Investigation", 114, Rarity.UNCOMMON, mage.cards.o.OngoingInvestigation.class));
        cards.add(new SetCardInfo("Organic Extinction", 76, Rarity.RARE, mage.cards.o.OrganicExtinction.class));
        cards.add(new SetCardInfo("Orzhov Advokist", 77, Rarity.UNCOMMON, mage.cards.o.OrzhovAdvokist.class));
        cards.add(new SetCardInfo("Otherworldly Gaze", 115, Rarity.COMMON, mage.cards.o.OtherworldlyGaze.class));
        cards.add(new SetCardInfo("Overseer of the Damned", 132, Rarity.RARE, mage.cards.o.OverseerOfTheDamned.class));
        cards.add(new SetCardInfo("Path of Ancestry", 279, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Path to Exile", 78, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 133, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 116, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Pile On", 134, Rarity.RARE, mage.cards.p.PileOn.class));
        cards.add(new SetCardInfo("Port of Karfell", 280, Rarity.UNCOMMON, mage.cards.p.PortOfKarfell.class));
        cards.add(new SetCardInfo("Prairie Stream", 281, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Price of Fame", 135, Rarity.UNCOMMON, mage.cards.p.PriceOfFame.class));
        cards.add(new SetCardInfo("Promise of Loyalty", 79, Rarity.RARE, mage.cards.p.PromiseOfLoyalty.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 234, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 136, Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Reanimate", 137, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reliquary Tower", 282, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Return of the Wildspeaker", 181, Rarity.RARE, mage.cards.r.ReturnOfTheWildspeaker.class));
        cards.add(new SetCardInfo("Rise of the Dark Realms", 138, Rarity.MYTHIC, mage.cards.r.RiseOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Rite of the Raging Storm", 159, Rarity.UNCOMMON, mage.cards.r.RiteOfTheRagingStorm.class));
        cards.add(new SetCardInfo("River of Tears", 283, Rarity.RARE, mage.cards.r.RiverOfTears.class));
        cards.add(new SetCardInfo("Rogue's Passage", 284, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Root Elemental", 182, Rarity.RARE, mage.cards.r.RootElemental.class));
        cards.add(new SetCardInfo("Sacred Peaks", 285, Rarity.COMMON, mage.cards.s.SacredPeaks.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 183, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Salt Road Ambushers", 184, Rarity.UNCOMMON, mage.cards.s.SaltRoadAmbushers.class));
        cards.add(new SetCardInfo("Saryth, the Viper's Fang", 185, Rarity.RARE, mage.cards.s.SarythTheVipersFang.class));
        cards.add(new SetCardInfo("Scattered Groves", 286, Rarity.RARE, mage.cards.s.ScatteredGroves.class));
        cards.add(new SetCardInfo("Scavenger Grounds", 287, Rarity.RARE, mage.cards.s.ScavengerGrounds.class));
        cards.add(new SetCardInfo("Scourge of the Throne", 160, Rarity.RARE, mage.cards.s.ScourgeOfTheThrone.class));
        cards.add(new SetCardInfo("Scroll of Fate", 235, Rarity.RARE, mage.cards.s.ScrollOfFate.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 80, Rarity.UNCOMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Search the Premises", 81, Rarity.RARE, mage.cards.s.SearchThePremises.class));
        cards.add(new SetCardInfo("Seaside Citadel", 288, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Secluded Steppe", 289, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Seedborn Muse", 186, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 290, Rarity.UNCOMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Selfless Squire", 82, Rarity.RARE, mage.cards.s.SelflessSquire.class));
        cards.add(new SetCardInfo("Selvala, Explorer Returned", 218, Rarity.RARE, mage.cards.s.SelvalaExplorerReturned.class));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 83, Rarity.RARE, mage.cards.s.SevinnesReclamation.class));
        cards.add(new SetCardInfo("Sheltered Thicket", 291, Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Shimmer Dragon", 117, Rarity.RARE, mage.cards.s.ShimmerDragon.class));
        cards.add(new SetCardInfo("Shiny Impetus", 161, Rarity.UNCOMMON, mage.cards.s.ShinyImpetus.class));
        cards.add(new SetCardInfo("Shriekmaw", 139, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Shrine of the Forsaken Gods", 292, Rarity.RARE, mage.cards.s.ShrineOfTheForsakenGods.class));
        cards.add(new SetCardInfo("Sidar Kondo of Jamuraa", 219, Rarity.MYTHIC, mage.cards.s.SidarKondoOfJamuraa.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 293, Rarity.UNCOMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Simic Signet", 236, Rarity.UNCOMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Sinister Starfish", 140, Rarity.COMMON, mage.cards.s.SinisterStarfish.class));
        cards.add(new SetCardInfo("Skycloud Expanse", 294, Rarity.RARE, mage.cards.s.SkycloudExpanse.class));
        cards.add(new SetCardInfo("Slayers' Stronghold", 295, Rarity.RARE, mage.cards.s.SlayersStronghold.class));
        cards.add(new SetCardInfo("Smuggler's Share", 84, Rarity.RARE, mage.cards.s.SmugglersShare.class));
        cards.add(new SetCardInfo("Sol Ring", 237, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 238, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Soul Snare", 85, Rarity.UNCOMMON, mage.cards.s.SoulSnare.class));
        cards.add(new SetCardInfo("Spectacular Showdown", 162, Rarity.RARE, mage.cards.s.SpectacularShowdown.class));
        cards.add(new SetCardInfo("Sphinx of the Second Sun", 118, Rarity.MYTHIC, mage.cards.s.SphinxOfTheSecondSun.class));
        cards.add(new SetCardInfo("Spire of Industry", 296, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Stalking Leonin", 86, Rarity.RARE, mage.cards.s.StalkingLeonin.class));
        cards.add(new SetCardInfo("Steel Hellkite", 239, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Sun Titan", 87, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Sungrass Prairie", 297, Rarity.RARE, mage.cards.s.SungrassPrairie.class));
        cards.add(new SetCardInfo("Sunhome, Fortress of the Legion", 298, Rarity.UNCOMMON, mage.cards.s.SunhomeFortressOfTheLegion.class));
        cards.add(new SetCardInfo("Sunken Hollow", 299, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 88, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Syr Konrad, the Grim", 141, Rarity.UNCOMMON, mage.cards.s.SyrKonradTheGrim.class));
        cards.add(new SetCardInfo("Tainted Isle", 300, Rarity.UNCOMMON, mage.cards.t.TaintedIsle.class));
        cards.add(new SetCardInfo("Talisman of Conviction", 240, Rarity.UNCOMMON, mage.cards.t.TalismanOfConviction.class));
        cards.add(new SetCardInfo("Talisman of Curiosity", 241, Rarity.UNCOMMON, mage.cards.t.TalismanOfCuriosity.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 242, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Progress", 243, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Talisman of Unity", 244, Rarity.UNCOMMON, mage.cards.t.TalismanOfUnity.class));
        cards.add(new SetCardInfo("Teferi's Ageless Insight", 119, Rarity.RARE, mage.cards.t.TeferisAgelessInsight.class));
        cards.add(new SetCardInfo("Temple of Abandon", 301, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 302, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Mystery", 303, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Plenty", 304, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Temple of Triumph", 306, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Temple of the False God", 305, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Temur War Shaman", 187, Rarity.RARE, mage.cards.t.TemurWarShaman.class));
        cards.add(new SetCardInfo("Tezzeret, Betrayer of Flesh", 120, Rarity.MYTHIC, mage.cards.t.TezzeretBetrayerOfFlesh.class));
        cards.add(new SetCardInfo("Thelonite Hermit", 188, Rarity.RARE, mage.cards.t.TheloniteHermit.class));
        cards.add(new SetCardInfo("Thought Monitor", 121, Rarity.RARE, mage.cards.t.ThoughtMonitor.class));
        cards.add(new SetCardInfo("Thought Vessel", 245, Rarity.UNCOMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thoughtbound Phantasm", 122, Rarity.UNCOMMON, mage.cards.t.ThoughtboundPhantasm.class));
        cards.add(new SetCardInfo("Three Visits", 189, Rarity.UNCOMMON, mage.cards.t.ThreeVisits.class));
        cards.add(new SetCardInfo("Throne of the High City", 307, Rarity.RARE, mage.cards.t.ThroneOfTheHighCity.class));
        cards.add(new SetCardInfo("Tireless Tracker", 190, Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Tocasia's Dig Site", 308, Rarity.COMMON, mage.cards.t.TocasiasDigSite.class));
        cards.add(new SetCardInfo("Tome of Legends", 246, Rarity.RARE, mage.cards.t.TomeOfLegends.class));
        cards.add(new SetCardInfo("Toski, Bearer of Secrets", 191, Rarity.RARE, mage.cards.t.ToskiBearerOfSecrets.class));
        cards.add(new SetCardInfo("Toxic Deluge", 142, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Trail of Mystery", 192, Rarity.RARE, mage.cards.t.TrailOfMystery.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 309, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Twilight Prophet", 143, Rarity.MYTHIC, mage.cards.t.TwilightProphet.class));
        cards.add(new SetCardInfo("Ugin's Mastery", 53, Rarity.RARE, mage.cards.u.UginsMastery.class));
        cards.add(new SetCardInfo("Ulvenwald Mysteries", 193, Rarity.UNCOMMON, mage.cards.u.UlvenwaldMysteries.class));
        cards.add(new SetCardInfo("Vengeful Ancestor", 163, Rarity.RARE, mage.cards.v.VengefulAncestor.class));
        cards.add(new SetCardInfo("Vizier of Many Faces", 123, Rarity.RARE, mage.cards.v.VizierOfManyFaces.class));
        cards.add(new SetCardInfo("Vow of Duty", 89, Rarity.UNCOMMON, mage.cards.v.VowOfDuty.class));
        cards.add(new SetCardInfo("Vow of Lightning", 164, Rarity.UNCOMMON, mage.cards.v.VowOfLightning.class));
        cards.add(new SetCardInfo("Wall of Omens", 90, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("War Room", 310, Rarity.RARE, mage.cards.w.WarRoom.class));
        cards.add(new SetCardInfo("Wavesifter", 220, Rarity.COMMON, mage.cards.w.Wavesifter.class));
        cards.add(new SetCardInfo("Welcoming Vampire", 91, Rarity.RARE, mage.cards.w.WelcomingVampire.class));
        cards.add(new SetCardInfo("Whirler Rogue", 124, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Whispering Snitch", 144, Rarity.UNCOMMON, mage.cards.w.WhisperingSnitch.class));
        cards.add(new SetCardInfo("Whisperwood Elemental", 194, Rarity.MYTHIC, mage.cards.w.WhisperwoodElemental.class));
        cards.add(new SetCardInfo("Wild Growth", 195, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Wilderness Reclamation", 196, Rarity.UNCOMMON, mage.cards.w.WildernessReclamation.class));
        cards.add(new SetCardInfo("Windborn Muse", 92, Rarity.RARE, mage.cards.w.WindbornMuse.class));
        cards.add(new SetCardInfo("Winds of Rath", 93, Rarity.RARE, mage.cards.w.WindsOfRath.class));
        cards.add(new SetCardInfo("Yedora, Grave Gardener", 197, Rarity.UNCOMMON, mage.cards.y.YedoraGraveGardener.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 311, Rarity.UNCOMMON, mage.cards.z.ZoeticCavern.class));
    }
}
