package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class TalesOfMiddleEarthCommander extends ExpansionSet {

    private static final TalesOfMiddleEarthCommander instance = new TalesOfMiddleEarthCommander();

    public static TalesOfMiddleEarthCommander getInstance() {
        return instance;
    }

    private TalesOfMiddleEarthCommander() {
        super("Tales of Middle-earth Commander", "LTC", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abyssal Persecutor", 525, Rarity.MYTHIC, mage.cards.a.AbyssalPersecutor.class));
        cards.add(new SetCardInfo("Access Tunnel", 294, Rarity.UNCOMMON, mage.cards.a.AccessTunnel.class));
        cards.add(new SetCardInfo("Ancient Tomb", 357, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Anduril, Narsil Reforged", 491, Rarity.RARE, mage.cards.a.AndurilNarsilReforged.class));
        cards.add(new SetCardInfo("Anger", 210, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Anguished Unmaking", 265, Rarity.RARE, mage.cards.a.AnguishedUnmaking.class));
        cards.add(new SetCardInfo("Aragorn, Hornburg Hero", 492, Rarity.MYTHIC, mage.cards.a.AragornHornburgHero.class));
        cards.add(new SetCardInfo("Aragorn, King of Gondor", 5, Rarity.MYTHIC, mage.cards.a.AragornKingOfGondor.class));
        cards.add(new SetCardInfo("Arbor Elf", 232, Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Arboreal Alliance", 497, Rarity.RARE, mage.cards.a.ArborealAlliance.class));
        cards.add(new SetCardInfo("Arcane Denial", 184, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Signet", 273, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Archivist of Gondor", 18, Rarity.RARE, mage.cards.a.ArchivistOfGondor.class));
        cards.add(new SetCardInfo("Arwen, Weaver of Hope", 35, Rarity.RARE, mage.cards.a.ArwenWeaverOfHope.class));
        cards.add(new SetCardInfo("Asceticism", 233, Rarity.RARE, mage.cards.a.Asceticism.class));
        cards.add(new SetCardInfo("Ash Barrens", 295, Rarity.UNCOMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Assemble the Entmoot", 36, Rarity.RARE, mage.cards.a.AssembleTheEntmoot.class));
        cards.add(new SetCardInfo("Banishing Light", 161, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Banquet Guests", 47, Rarity.RARE, mage.cards.b.BanquetGuests.class));
        cards.add(new SetCardInfo("Basalt Monolith", 274, Rarity.UNCOMMON, mage.cards.b.BasaltMonolith.class));
        cards.add(new SetCardInfo("Bastion Protector", 162, Rarity.RARE, mage.cards.b.BastionProtector.class));
        cards.add(new SetCardInfo("Battlefield Forge", 296, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Beast Within", 234, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Beregond of the Guard", 9, Rarity.RARE, mage.cards.b.BeregondOfTheGuard.class));
        cards.add(new SetCardInfo("Bilbo, Birthday Celebrant", 48, Rarity.RARE, mage.cards.b.BilboBirthdayCelebrant.class));
        cards.add(new SetCardInfo("Birds of Paradise", 235, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Blasphemous Act", 211, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Bojuka Bog", 358, Rarity.MYTHIC, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Boon of the Wish-Giver", 185, Rarity.UNCOMMON, mage.cards.b.BoonOfTheWishGiver.class));
        cards.add(new SetCardInfo("Boromir, Gondor's Hope", 49, Rarity.RARE, mage.cards.b.BoromirGondorsHope.class));
        cards.add(new SetCardInfo("Boseiju, Who Shelters All", 359, Rarity.MYTHIC, mage.cards.b.BoseijuWhoSheltersAll.class));
        cards.add(new SetCardInfo("Brushland", 297, Rarity.RARE, mage.cards.b.Brushland.class));
        cards.add(new SetCardInfo("Cabal Coffers", 360, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Call Forth the Tempest", 509, Rarity.RARE, mage.cards.c.CallForthTheTempest.class));
        cards.add(new SetCardInfo("Call for Aid", 30, Rarity.RARE, mage.cards.c.CallForAid.class));
        cards.add(new SetCardInfo("Call for Unity", 163, Rarity.RARE, mage.cards.c.CallForUnity.class));
        cards.add(new SetCardInfo("Canopy Vista", 298, Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Castle Ardenvale", 361, Rarity.MYTHIC, mage.cards.c.CastleArdenvale.class));
        cards.add(new SetCardInfo("Cavern of Souls", "392z", Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cavern-Hoard Dragon", 31, Rarity.RARE, mage.cards.c.CavernHoardDragon.class));
        cards.add(new SetCardInfo("Champions of Minas Tirith", 10, Rarity.RARE, mage.cards.c.ChampionsOfMinasTirith.class));
        cards.add(new SetCardInfo("Choked Estuary", 299, Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 275, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Cirdan the Shipwright", 50, Rarity.RARE, mage.cards.c.CirdanTheShipwright.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 300, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Cloudstone Curio", 349, Rarity.MYTHIC, mage.cards.c.CloudstoneCurio.class));
        cards.add(new SetCardInfo("Colossal Whale", 186, Rarity.RARE, mage.cards.c.ColossalWhale.class));
        cards.add(new SetCardInfo("Combat Celebrant", 212, Rarity.MYTHIC, mage.cards.c.CombatCelebrant.class));
        cards.add(new SetCardInfo("Command Tower", 301, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 276, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Consider", 187, Rarity.COMMON, mage.cards.c.Consider.class));
        cards.add(new SetCardInfo("Corsairs of Umbar", 19, Rarity.RARE, mage.cards.c.CorsairsOfUmbar.class));
        cards.add(new SetCardInfo("Courageous Resolve", 506, Rarity.RARE, mage.cards.c.CourageousResolve.class));
        cards.add(new SetCardInfo("Court of Ire", 213, Rarity.RARE, mage.cards.c.CourtOfIre.class));
        cards.add(new SetCardInfo("Crown of Gondor", 75, Rarity.RARE, mage.cards.c.CrownOfGondor.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 302, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Crypt Incursion", 198, Rarity.COMMON, mage.cards.c.CryptIncursion.class));
        cards.add(new SetCardInfo("Cultivate", 236, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Dawn of Hope", 164, Rarity.RARE, mage.cards.d.DawnOfHope.class));
        cards.add(new SetCardInfo("Dearly Departed", 165, Rarity.RARE, mage.cards.d.DearlyDeparted.class));
        cards.add(new SetCardInfo("Decree of Pain", 199, Rarity.RARE, mage.cards.d.DecreeOfPain.class));
        cards.add(new SetCardInfo("Deep Analysis", 188, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Denethor, Stone Seer", 20, Rarity.RARE, mage.cards.d.DenethorStoneSeer.class));
        cards.add(new SetCardInfo("Deserted Temple", 363, Rarity.MYTHIC, mage.cards.d.DesertedTemple.class));
        cards.add(new SetCardInfo("Desolate Lighthouse", 303, Rarity.RARE, mage.cards.d.DesolateLighthouse.class));
        cards.add(new SetCardInfo("Devastation Tide", 189, Rarity.RARE, mage.cards.d.DevastationTide.class));
        cards.add(new SetCardInfo("Diabolic Intent", 526, Rarity.MYTHIC, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Door of Destinies", 277, Rarity.RARE, mage.cards.d.DoorOfDestinies.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 517, Rarity.RARE, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 304, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 305, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Dusk // Dawn", 166, Rarity.RARE, mage.cards.d.DuskDawn.class));
        cards.add(new SetCardInfo("Earthquake", 214, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Elrond of the White Council", 51, Rarity.RARE, mage.cards.e.ElrondOfTheWhiteCouncil.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 237, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Harbinger", 527, Rarity.UNCOMMON, mage.cards.e.ElvishHarbinger.class));
        cards.add(new SetCardInfo("Elvish Mystic", 238, Rarity.COMMON, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Elvish Piper", 239, Rarity.RARE, mage.cards.e.ElvishPiper.class));
        cards.add(new SetCardInfo("Elvish Visionary", 240, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Elvish Warmaster", 241, Rarity.RARE, mage.cards.e.ElvishWarmaster.class));
        cards.add(new SetCardInfo("Ensnaring Bridge", 350, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Eomer, King of Rohan", 52, Rarity.RARE, mage.cards.e.EomerKingOfRohan.class));
        cards.add(new SetCardInfo("Eowyn, Shieldmaiden", 1, Rarity.MYTHIC, mage.cards.e.EowynShieldmaiden.class));
        cards.add(new SetCardInfo("Erestor of the Council", 53, Rarity.RARE, mage.cards.e.ErestorOfTheCouncil.class));
        cards.add(new SetCardInfo("Essence Warden", 242, Rarity.COMMON, mage.cards.e.EssenceWarden.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 278, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 306, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exotic Orchard", 307, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Explore", 528, Rarity.UNCOMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Extract from Darkness", 266, Rarity.UNCOMMON, mage.cards.e.ExtractFromDarkness.class));
        cards.add(new SetCardInfo("Fact or Fiction", 190, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Faithless Looting", 215, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Faramir, Steward of Gondor", 54, Rarity.RARE, mage.cards.f.FaramirStewardOfGondor.class));
        cards.add(new SetCardInfo("Farhaven Elf", 243, Rarity.COMMON, mage.cards.f.FarhavenElf.class));
        cards.add(new SetCardInfo("Farmer Cotton", 55, Rarity.RARE, mage.cards.f.FarmerCotton.class));
        cards.add(new SetCardInfo("Farseek", 244, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fealty to the Realm", 21, Rarity.RARE, mage.cards.f.FealtyToTheRealm.class));
        cards.add(new SetCardInfo("Feasting Hobbit", 37, Rarity.RARE, mage.cards.f.FeastingHobbit.class));
        cards.add(new SetCardInfo("Feed the Swarm", 200, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fell Beast of Mordor", 513, Rarity.RARE, mage.cards.f.FellBeastOfMordor.class));
        cards.add(new SetCardInfo("Fell Beast's Shriek", 508, Rarity.RARE, mage.cards.f.FellBeastsShriek.class));
        cards.add(new SetCardInfo("Fell the Mighty", 167, Rarity.RARE, mage.cards.f.FellTheMighty.class));
        cards.add(new SetCardInfo("Field of Ruin", 308, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Field-Tested Frying Pan", 11, Rarity.RARE, mage.cards.f.FieldTestedFryingPan.class));
        cards.add(new SetCardInfo("Fiend Hunter", 168, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Flamerush Rider", 216, Rarity.RARE, mage.cards.f.FlamerushRider.class));
        cards.add(new SetCardInfo("Flooded Grove", 309, Rarity.RARE, mage.cards.f.FloodedGrove.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 191, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Foreboding Ruins", 310, Rarity.RARE, mage.cards.f.ForebodingRuins.class));
        cards.add(new SetCardInfo("Forth Eorlingas!", 56, Rarity.RARE, mage.cards.f.ForthEorlingas.class));
        cards.add(new SetCardInfo("Fortified Village", 311, Rarity.RARE, mage.cards.f.FortifiedVillage.class));
        cards.add(new SetCardInfo("Frodo, Adventurous Hobbit", 2, Rarity.MYTHIC, mage.cards.f.FrodoAdventurousHobbit.class));
        cards.add(new SetCardInfo("Frontier Warmonger", 217, Rarity.RARE, mage.cards.f.FrontierWarmonger.class));
        cards.add(new SetCardInfo("Frontline Medic", 169, Rarity.RARE, mage.cards.f.FrontlineMedic.class));
        cards.add(new SetCardInfo("Frostboil Snarl", 312, Rarity.RARE, mage.cards.f.FrostboilSnarl.class));
        cards.add(new SetCardInfo("Fumigate", 170, Rarity.RARE, mage.cards.f.Fumigate.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 313, Rarity.RARE, mage.cards.f.FurycalmSnarl.class));
        cards.add(new SetCardInfo("Galadhrim Ambush", 38, Rarity.RARE, mage.cards.g.GaladhrimAmbush.class));
        cards.add(new SetCardInfo("Galadhrim Brigade", 502, Rarity.RARE, mage.cards.g.GaladhrimBrigade.class));
        cards.add(new SetCardInfo("Galadriel's Dismissal", 500, Rarity.RARE, mage.cards.g.GaladrielsDismissal.class));
        cards.add(new SetCardInfo("Galadriel, Elven-Queen", 3, Rarity.MYTHIC, mage.cards.g.GaladrielElvenQueen.class));
        cards.add(new SetCardInfo("Galadriel, Light of Valinor", 498, Rarity.MYTHIC, mage.cards.g.GaladrielLightOfValinor.class));
        cards.add(new SetCardInfo("Gandalf, Westward Voyager", 6, Rarity.MYTHIC, mage.cards.g.GandalfWestwardVoyager.class));
        cards.add(new SetCardInfo("Gemstone Caverns", 364, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class));
        cards.add(new SetCardInfo("Genesis Wave", 245, Rarity.RARE, mage.cards.g.GenesisWave.class));
        cards.add(new SetCardInfo("Ghost Quarter", 314, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Gilded Goose", 246, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Gilraen, Dunedain Protector", 13, Rarity.RARE, mage.cards.g.GilraenDunedainProtector.class));
        cards.add(new SetCardInfo("Gimli of the Glittering Caves", 32, Rarity.RARE, mage.cards.g.GimliOfTheGlitteringCaves.class));
        cards.add(new SetCardInfo("Gimli's Reckless Might", 494, Rarity.RARE, mage.cards.g.GimlisRecklessMight.class));
        cards.add(new SetCardInfo("Glacial Fortress", 315, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Go for the Throat", 201, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Goblin Cratermaker", 218, Rarity.UNCOMMON, mage.cards.g.GoblinCratermaker.class));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", 219, Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class));
        cards.add(new SetCardInfo("Gollum, Obsessed Stalker", 26, Rarity.RARE, mage.cards.g.GollumObsessedStalker.class));
        cards.add(new SetCardInfo("Graypelt Refuge", 316, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Great Oak Guardian", 247, Rarity.UNCOMMON, mage.cards.g.GreatOakGuardian.class));
        cards.add(new SetCardInfo("Grey Host Reinforcements", 14, Rarity.RARE, mage.cards.g.GreyHostReinforcements.class));
        cards.add(new SetCardInfo("Grima, Saruman's Footman", 57, Rarity.RARE, mage.cards.g.GrimaSarumansFootman.class));
        cards.add(new SetCardInfo("Growth Spiral", 267, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Guttersnipe", 220, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Gwaihir, Greatest of the Eagles", 15, Rarity.RARE, mage.cards.g.GwaihirGreatestOfTheEagles.class));
        cards.add(new SetCardInfo("Haldir, Lorien Lieutenant", 39, Rarity.RARE, mage.cards.h.HaldirLorienLieutenant.class));
        cards.add(new SetCardInfo("Hammerheim", 518, Rarity.RARE, mage.cards.h.Hammerheim.class));
        cards.add(new SetCardInfo("Harmonize", 248, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Harsh Mentor", 221, Rarity.RARE, mage.cards.h.HarshMentor.class));
        cards.add(new SetCardInfo("Heirloom Blade", 279, Rarity.UNCOMMON, mage.cards.h.HeirloomBlade.class));
        cards.add(new SetCardInfo("Herald's Horn", 280, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Heroic Intervention", 249, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 317, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Hithlain Rope", 76, Rarity.RARE, mage.cards.h.HithlainRope.class));
        cards.add(new SetCardInfo("Homeward Path", 365, Rarity.MYTHIC, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Horizon Canopy", 366, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class));
        cards.add(new SetCardInfo("Hornet Queen", 250, Rarity.RARE, mage.cards.h.HornetQueen.class));
        cards.add(new SetCardInfo("Hostage Taker", 268, Rarity.RARE, mage.cards.h.HostageTaker.class));
        cards.add(new SetCardInfo("Humble Defector", 222, Rarity.UNCOMMON, mage.cards.h.HumbleDefector.class));
        cards.add(new SetCardInfo("In the Darkness Bind Them", 58, Rarity.RARE, mage.cards.i.InTheDarknessBindThem.class));
        cards.add(new SetCardInfo("Increasing Devotion", 171, Rarity.RARE, mage.cards.i.IncreasingDevotion.class));
        cards.add(new SetCardInfo("Inferno Titan", 223, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
        cards.add(new SetCardInfo("Inscription of Abundance", 251, Rarity.RARE, mage.cards.i.InscriptionOfAbundance.class));
        cards.add(new SetCardInfo("Isengard Unleashed", 495, Rarity.RARE, mage.cards.i.IsengardUnleashed.class));
        cards.add(new SetCardInfo("Ishkanah, Grafwidow", 516, Rarity.MYTHIC, mage.cards.i.IshkanahGrafwidow.class));
        cards.add(new SetCardInfo("Isolated Chapel", 318, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Karakas", 367, Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Kenrith, the Returned King", 515, Rarity.MYTHIC, mage.cards.k.KenrithTheReturnedKing.class));
        cards.add(new SetCardInfo("Knollspine Dragon", 224, Rarity.RARE, mage.cards.k.KnollspineDragon.class));
        cards.add(new SetCardInfo("Kor Haven", 368, Rarity.MYTHIC, mage.cards.k.KorHaven.class));
        cards.add(new SetCardInfo("Languish", 202, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Learn from the Past", 192, Rarity.UNCOMMON, mage.cards.l.LearnFromThePast.class));
        cards.add(new SetCardInfo("Legolas Greenleaf", 40, Rarity.RARE, mage.cards.l.LegolasGreenleaf.class));
        cards.add(new SetCardInfo("Legolas's Quick Reflexes", 493, Rarity.RARE, mage.cards.l.LegolassQuickReflexes.class));
        cards.add(new SetCardInfo("Lidless Gaze", 59, Rarity.RARE, mage.cards.l.LidlessGaze.class));
        cards.add(new SetCardInfo("Lightning Greaves", 281, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Lignify", 252, Rarity.COMMON, mage.cards.l.Lignify.class));
        cards.add(new SetCardInfo("Living Death", 203, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Lobelia, Defender of Bag End", 27, Rarity.RARE, mage.cards.l.LobeliaDefenderOfBagEnd.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 319, Rarity.UNCOMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Lord of the Nazgul", 60, Rarity.RARE, mage.cards.l.LordOfTheNazgul.class));
        cards.add(new SetCardInfo("Lossarnach Captain", 16, Rarity.RARE, mage.cards.l.LossarnachCaptain.class));
        cards.add(new SetCardInfo("Lothlorien Blade", 77, Rarity.RARE, mage.cards.l.LothlorienBlade.class));
        cards.add(new SetCardInfo("Marshal's Anthem", 172, Rarity.RARE, mage.cards.m.MarshalsAnthem.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 173, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Merciless Executioner", 204, Rarity.UNCOMMON, mage.cards.m.MercilessExecutioner.class));
        cards.add(new SetCardInfo("Merry, Warden of Isengard", 61, Rarity.RARE, mage.cards.m.MerryWardenOfIsengard.class));
        cards.add(new SetCardInfo("Minamo, School at Water's Edge", 369, Rarity.MYTHIC, mage.cards.m.MinamoSchoolAtWatersEdge.class));
        cards.add(new SetCardInfo("Minas Morgul, Dark Fortress", 514, Rarity.RARE, mage.cards.m.MinasMorgulDarkFortress.class));
        cards.add(new SetCardInfo("Mind Stone", 282, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mirkwood Elk", 41, Rarity.RARE, mage.cards.m.MirkwoodElk.class));
        cards.add(new SetCardInfo("Mirkwood Trapper", 62, Rarity.RARE, mage.cards.m.MirkwoodTrapper.class));
        cards.add(new SetCardInfo("Mists of Lorien", 501, Rarity.RARE, mage.cards.m.MistsOfLorien.class));
        cards.add(new SetCardInfo("Model of Unity", 78, Rarity.RARE, mage.cards.m.ModelOfUnity.class));
        cards.add(new SetCardInfo("Monstrosity of the Lake", 22, Rarity.RARE, mage.cards.m.MonstrosityOfTheLake.class));
        cards.add(new SetCardInfo("Mordor on the March", 512, Rarity.RARE, mage.cards.m.MordorOnTheMarch.class));
        cards.add(new SetCardInfo("Moria Scavenger", 63, Rarity.RARE, mage.cards.m.MoriaScavenger.class));
        cards.add(new SetCardInfo("Mortify", 269, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Motivated Pony", 42, Rarity.RARE, mage.cards.m.MotivatedPony.class));
        cards.add(new SetCardInfo("Mouth of Ronom", 370, Rarity.MYTHIC, mage.cards.m.MouthOfRonom.class));
        cards.add(new SetCardInfo("Murmuring Bosk", 320, Rarity.RARE, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Myriad Landscape", 534, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Mystic Confluence", 193, Rarity.RARE, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Nazgul Battle-Mace", 510, Rarity.RARE, mage.cards.n.NazgulBattleMace.class));
        cards.add(new SetCardInfo("Necroblossom Snarl", 321, Rarity.RARE, mage.cards.n.NecroblossomSnarl.class));
        cards.add(new SetCardInfo("Night's Whisper", 205, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Notion Thief", 270, Rarity.RARE, mage.cards.n.NotionThief.class));
        cards.add(new SetCardInfo("Oath of Eorl", 64, Rarity.RARE, mage.cards.o.OathOfEorl.class));
        cards.add(new SetCardInfo("Oboro, Palace in the Clouds", 371, Rarity.MYTHIC, mage.cards.o.OboroPalaceInTheClouds.class));
        cards.add(new SetCardInfo("Of Herbs and Stewed Rabbit", 17, Rarity.RARE, mage.cards.o.OfHerbsAndStewedRabbit.class));
        cards.add(new SetCardInfo("Olorin's Searing Light", 503, Rarity.RARE, mage.cards.o.OlorinsSearingLight.class));
        cards.add(new SetCardInfo("Opt", 194, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Orchard Strider", 253, Rarity.COMMON, mage.cards.o.OrchardStrider.class));
        cards.add(new SetCardInfo("Orcish Siegemaster", 33, Rarity.RARE, mage.cards.o.OrcishSiegemaster.class));
        cards.add(new SetCardInfo("Overwhelming Stampede", 254, Rarity.RARE, mage.cards.o.OverwhelmingStampede.class));
        cards.add(new SetCardInfo("Pact of Negation", 523, Rarity.RARE, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Palace Jailer", 174, Rarity.UNCOMMON, mage.cards.p.PalaceJailer.class));
        cards.add(new SetCardInfo("Paradise Druid", 255, Rarity.UNCOMMON, mage.cards.p.ParadiseDruid.class));
        cards.add(new SetCardInfo("Path of Ancestry", 322, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Path to Exile", 175, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pillar of the Paruns", 372, Rarity.MYTHIC, mage.cards.p.PillarOfTheParuns.class));
        cards.add(new SetCardInfo("Pippin, Warden of Isengard", 65, Rarity.RARE, mage.cards.p.PippinWardenOfIsengard.class));
        cards.add(new SetCardInfo("Plea for Power", 195, Rarity.RARE, mage.cards.p.PleaForPower.class));
        cards.add(new SetCardInfo("Port Town", 323, Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prairie Stream", 324, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Preordain", 196, Rarity.COMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Pristine Talisman", 283, Rarity.COMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Prize Pig", 43, Rarity.RARE, mage.cards.p.PrizePig.class));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 256, Rarity.UNCOMMON, mage.cards.p.ProsperousInnkeeper.class));
        cards.add(new SetCardInfo("Radagast, Wizard of Wilds", 66, Rarity.RARE, mage.cards.r.RadagastWizardOfWilds.class));
        cards.add(new SetCardInfo("Raise the Palisade", 23, Rarity.RARE, mage.cards.r.RaiseThePalisade.class));
        cards.add(new SetCardInfo("Rally the Galadhrim", 499, Rarity.RARE, mage.cards.r.RallyTheGaladhrim.class));
        cards.add(new SetCardInfo("Rammas Echor, Ancient Shield", 505, Rarity.RARE, mage.cards.r.RammasEchorAncientShield.class));
        cards.add(new SetCardInfo("Rampaging War Mammoth", 34, Rarity.RARE, mage.cards.r.RampagingWarMammoth.class));
        cards.add(new SetCardInfo("Rampant Growth", 257, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rapacious Guest", 28, Rarity.RARE, mage.cards.r.RapaciousGuest.class));
        cards.add(new SetCardInfo("Realm Seekers", 258, Rarity.RARE, mage.cards.r.RealmSeekers.class));
        cards.add(new SetCardInfo("Reanimate", 206, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reclamation Sage", 259, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Reflecting Pool", 373, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Rejuvenating Springs", 325, Rarity.RARE, mage.cards.r.RejuvenatingSprings.class));
        cards.add(new SetCardInfo("Relic of Sauron", 79, Rarity.RARE, mage.cards.r.RelicOfSauron.class));
        cards.add(new SetCardInfo("Revenge of Ravens", 207, Rarity.UNCOMMON, mage.cards.r.RevengeOfRavens.class));
        cards.add(new SetCardInfo("Riders of Rohan", 67, Rarity.RARE, mage.cards.r.RidersOfRohan.class));
        cards.add(new SetCardInfo("Rings of Brighthearth", 352, Rarity.MYTHIC, mage.cards.r.RingsOfBrighthearth.class));
        cards.add(new SetCardInfo("River Kelpie", 524, Rarity.RARE, mage.cards.r.RiverKelpie.class));
        cards.add(new SetCardInfo("Rogue's Passage", 326, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rohirrim Chargers", 496, Rarity.RARE, mage.cards.r.RohirrimChargers.class));
        cards.add(new SetCardInfo("Sail into the West", 68, Rarity.RARE, mage.cards.s.SailIntoTheWest.class));
        cards.add(new SetCardInfo("Sam, Loyal Attendant", 7, Rarity.MYTHIC, mage.cards.s.SamLoyalAttendant.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 327, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sanguine Bond", 208, Rarity.RARE, mage.cards.s.SanguineBond.class));
        cards.add(new SetCardInfo("Saruman, the White Hand", 8, Rarity.MYTHIC, mage.cards.s.SarumanTheWhiteHand.class));
        cards.add(new SetCardInfo("Sauron, Lord of the Rings", 4, Rarity.MYTHIC, mage.cards.s.SauronLordOfTheRings.class));
        cards.add(new SetCardInfo("Savvy Hunter", 271, Rarity.UNCOMMON, mage.cards.s.SavvyHunter.class));
        cards.add(new SetCardInfo("Scattered Groves", 328, Rarity.RARE, mage.cards.s.ScatteredGroves.class));
        cards.add(new SetCardInfo("Scoured Barrens", 329, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scourge of the Throne", 225, Rarity.MYTHIC, mage.cards.s.ScourgeOfTheThrone.class));
        cards.add(new SetCardInfo("Seasons Past", 529, Rarity.RARE, mage.cards.s.SeasonsPast.class));
        cards.add(new SetCardInfo("Secluded Courtyard", 330, Rarity.UNCOMMON, mage.cards.s.SecludedCourtyard.class));
        cards.add(new SetCardInfo("Second Harvest", 530, Rarity.RARE, mage.cards.s.SecondHarvest.class));
        cards.add(new SetCardInfo("Seeds of Renewal", 260, Rarity.RARE, mage.cards.s.SeedsOfRenewal.class));
        cards.add(new SetCardInfo("Selfless Squire", 176, Rarity.RARE, mage.cards.s.SelflessSquire.class));
        cards.add(new SetCardInfo("Shadowspear", 353, Rarity.MYTHIC, mage.cards.s.Shadowspear.class));
        cards.add(new SetCardInfo("Shared Animosity", 226, Rarity.RARE, mage.cards.s.SharedAnimosity.class));
        cards.add(new SetCardInfo("Shelob, Dread Weaver", 29, Rarity.RARE, mage.cards.s.ShelobDreadWeaver.class));
        cards.add(new SetCardInfo("Shineshadow Snarl", 331, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class));
        cards.add(new SetCardInfo("Shinka, the Bloodsoaked Keep", 374, Rarity.MYTHIC, mage.cards.s.ShinkaTheBloodsoakedKeep.class));
        cards.add(new SetCardInfo("Shiny Impetus", 227, Rarity.UNCOMMON, mage.cards.s.ShinyImpetus.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 228, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 332, Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Sol Ring", 284, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Song of Earendil", 69, Rarity.RARE, mage.cards.s.SongOfEarendil.class));
        cards.add(new SetCardInfo("Sorcerous Squall", 504, Rarity.RARE, mage.cards.s.SorcerousSquall.class));
        cards.add(new SetCardInfo("Soul's Attendant", 520, Rarity.UNCOMMON, mage.cards.s.SoulsAttendant.class));
        cards.add(new SetCardInfo("Stonehewer Giant", 521, Rarity.RARE, mage.cards.s.StonehewerGiant.class));
        cards.add(new SetCardInfo("Subjugate the Hobbits", 24, Rarity.RARE, mage.cards.s.SubjugateTheHobbits.class));
        cards.add(new SetCardInfo("Sulfur Falls", 333, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Sulfurous Springs", 334, Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Summons of Saruman", 70, Rarity.RARE, mage.cards.s.SummonsOfSaruman.class));
        cards.add(new SetCardInfo("Sunken Hollow", 335, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 336, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Sunset Revelry", 177, Rarity.UNCOMMON, mage.cards.s.SunsetRevelry.class));
        cards.add(new SetCardInfo("Supreme Verdict", 272, Rarity.RARE, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Swan Song", 197, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 354, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class));
        cards.add(new SetCardInfo("Sword of the Animist", 355, Rarity.MYTHIC, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 178, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Offering", 261, Rarity.RARE, mage.cards.s.SylvanOffering.class));
        cards.add(new SetCardInfo("Sylvan Tutor", 531, Rarity.MYTHIC, mage.cards.s.SylvanTutor.class));
        cards.add(new SetCardInfo("Talisman of Conviction", 285, Rarity.UNCOMMON, mage.cards.t.TalismanOfConviction.class));
        cards.add(new SetCardInfo("Talisman of Progress", 286, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Taunt from the Rampart", 71, Rarity.RARE, mage.cards.t.TauntFromTheRampart.class));
        cards.add(new SetCardInfo("Tempt with Discovery", 532, Rarity.UNCOMMON, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 337, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("The Balrog of Moria", 46, Rarity.RARE, mage.cards.t.TheBalrogOfMoria.class));
        cards.add(new SetCardInfo("The Black Gate", 80, Rarity.RARE, mage.cards.t.TheBlackGate.class));
        cards.add(new SetCardInfo("The Gaffer", 12, Rarity.RARE, mage.cards.t.TheGaffer.class));
        cards.add(new SetCardInfo("The Great Henge", 348, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Ozolith", 351, Rarity.MYTHIC, mage.cards.t.TheOzolith.class));
        cards.add(new SetCardInfo("Thorn of Amethyst", 356, Rarity.MYTHIC, mage.cards.t.ThornOfAmethyst.class));
        cards.add(new SetCardInfo("Thornwood Falls", 338, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Vessel", 287, Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 229, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Throne of the High City", 339, Rarity.RARE, mage.cards.t.ThroneOfTheHighCity.class));
        cards.add(new SetCardInfo("Timber Protector", 533, Rarity.RARE, mage.cards.t.TimberProtector.class));
        cards.add(new SetCardInfo("Tireless Provisioner", 262, Rarity.UNCOMMON, mage.cards.t.TirelessProvisioner.class));
        cards.add(new SetCardInfo("Too Greedily, Too Deep", 72, Rarity.RARE, mage.cards.t.TooGreedilyTooDeep.class));
        cards.add(new SetCardInfo("Toxic Deluge", 209, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Trading Post", 288, Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Tranquil Cove", 340, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 341, Rarity.UNCOMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Trap the Trespassers", 25, Rarity.RARE, mage.cards.t.TrapTheTrespassers.class));
        cards.add(new SetCardInfo("Travel Through Caradhras", 44, Rarity.RARE, mage.cards.t.TravelThroughCaradhras.class));
        cards.add(new SetCardInfo("Treasure Nabber", 230, Rarity.RARE, mage.cards.t.TreasureNabber.class));
        cards.add(new SetCardInfo("Treebeard, Gracious Host", 73, Rarity.RARE, mage.cards.t.TreebeardGraciousHost.class));
        cards.add(new SetCardInfo("Unbreakable Formation", 179, Rarity.RARE, mage.cards.u.UnbreakableFormation.class));
        cards.add(new SetCardInfo("Underground River", 342, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Urborg", 519, Rarity.RARE, mage.cards.u.Urborg.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 375, Rarity.MYTHIC, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", 289, Rarity.RARE, mage.cards.v.VanquishersBanner.class));
        cards.add(new SetCardInfo("Verge Rangers", 180, Rarity.RARE, mage.cards.v.VergeRangers.class));
        cards.add(new SetCardInfo("Village Bell-Ringer", 181, Rarity.COMMON, mage.cards.v.VillageBellRinger.class));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 343, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class));
        cards.add(new SetCardInfo("Visions of Glory", 182, Rarity.RARE, mage.cards.v.VisionsOfGlory.class));
        cards.add(new SetCardInfo("Wake the Dragon", 74, Rarity.RARE, mage.cards.w.WakeTheDragon.class));
        cards.add(new SetCardInfo("Wasteland", 376, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 290, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Weathered Wayfarer", 183, Rarity.RARE, mage.cards.w.WeatheredWayfarer.class));
        cards.add(new SetCardInfo("Well of Lost Dreams", 291, Rarity.RARE, mage.cards.w.WellOfLostDreams.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 292, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 344, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Windbrisk Heights", 345, Rarity.RARE, mage.cards.w.WindbriskHeights.class));
        cards.add(new SetCardInfo("Windswift Slice", 45, Rarity.RARE, mage.cards.w.WindswiftSlice.class));
        cards.add(new SetCardInfo("Witch-king, Sky Scourge", 511, Rarity.MYTHIC, mage.cards.w.WitchKingSkyScourge.class));
        cards.add(new SetCardInfo("Wood Elves", 263, Rarity.COMMON, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Woodfall Primus", 264, Rarity.RARE, mage.cards.w.WoodfallPrimus.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 346, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));
        cards.add(new SetCardInfo("Woodland Stream", 347, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("Worn Powerstone", 293, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Worship", 522, Rarity.RARE, mage.cards.w.Worship.class));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 377, Rarity.MYTHIC, mage.cards.y.YavimayaCradleOfGrowth.class));
        cards.add(new SetCardInfo("Zealous Conscripts", 231, Rarity.RARE, mage.cards.z.ZealousConscripts.class));
    }
}
