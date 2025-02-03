package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Aetherdrift extends ExpansionSet {

    private static final Aetherdrift instance = new Aetherdrift();

    public static Aetherdrift getInstance() {
        return instance;
    }

    private Aetherdrift() {
        super("Aetherdrift", "DFT", ExpansionSet.buildDate(2025, 2, 14), SetType.EXPANSION);
        this.blockName = "Aetherdrift"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aatchik, Emerald Radian", 187, Rarity.RARE, mage.cards.a.AatchikEmeraldRadian.class));
        cards.add(new SetCardInfo("Adrenaline Jockey", 112, Rarity.UNCOMMON, mage.cards.a.AdrenalineJockey.class));
        cards.add(new SetCardInfo("Aether Syphon", 38, Rarity.UNCOMMON, mage.cards.a.AetherSyphon.class));
        cards.add(new SetCardInfo("Aetherjacket", 230, Rarity.COMMON, mage.cards.a.Aetherjacket.class));
        cards.add(new SetCardInfo("Afterburner Expert", 150, Rarity.RARE, mage.cards.a.AfterburnerExpert.class));
        cards.add(new SetCardInfo("Agonasaur Rex", 151, Rarity.RARE, mage.cards.a.AgonasaurRex.class));
        cards.add(new SetCardInfo("Air Response Unit", 1, Rarity.UNCOMMON, mage.cards.a.AirResponseUnit.class));
        cards.add(new SetCardInfo("Alacrian Jaguar", 152, Rarity.COMMON, mage.cards.a.AlacrianJaguar.class));
        cards.add(new SetCardInfo("Amonkhet Raceway", 248, Rarity.UNCOMMON, mage.cards.a.AmonkhetRaceway.class));
        cards.add(new SetCardInfo("Apocalypse Runner", 188, Rarity.UNCOMMON, mage.cards.a.ApocalypseRunner.class));
        cards.add(new SetCardInfo("Avishkar Raceway", 249, Rarity.COMMON, mage.cards.a.AvishkarRaceway.class));
        cards.add(new SetCardInfo("Back on Track", 76, Rarity.UNCOMMON, mage.cards.b.BackOnTrack.class));
        cards.add(new SetCardInfo("Basri, Tomorrow's Champion", 3, Rarity.RARE, mage.cards.b.BasriTomorrowsChampion.class));
        cards.add(new SetCardInfo("Beastrider Vanguard", 154, Rarity.COMMON, mage.cards.b.BeastriderVanguard.class));
        cards.add(new SetCardInfo("Bestow Greatness", 155, Rarity.COMMON, mage.cards.b.BestowGreatness.class));
        cards.add(new SetCardInfo("Bleachbone Verge", 250, Rarity.RARE, mage.cards.b.BleachboneVerge.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 251, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodghast", 77, Rarity.RARE, mage.cards.b.Bloodghast.class));
        cards.add(new SetCardInfo("Blossoming Sands", 252, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Boom Scholar", 189, Rarity.UNCOMMON, mage.cards.b.BoomScholar.class));
        cards.add(new SetCardInfo("Boosted Sloop", 190, Rarity.UNCOMMON, mage.cards.b.BoostedSloop.class));
        cards.add(new SetCardInfo("Bounce Off", 39, Rarity.COMMON, mage.cards.b.BounceOff.class));
        cards.add(new SetCardInfo("Brightfield Glider", 4, Rarity.COMMON, mage.cards.b.BrightfieldGlider.class));
        cards.add(new SetCardInfo("Brightfield Mustang", 5, Rarity.COMMON, mage.cards.b.BrightfieldMustang.class));
        cards.add(new SetCardInfo("Brightglass Gearhulk", 191, Rarity.MYTHIC, mage.cards.b.BrightglassGearhulk.class));
        cards.add(new SetCardInfo("Broadcast Rambler", 6, Rarity.COMMON, mage.cards.b.BroadcastRambler.class));
        cards.add(new SetCardInfo("Broadside Barrage", 192, Rarity.UNCOMMON, mage.cards.b.BroadsideBarrage.class));
        cards.add(new SetCardInfo("Broken Wings", 156, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Broodheart Engine", 193, Rarity.UNCOMMON, mage.cards.b.BroodheartEngine.class));
        cards.add(new SetCardInfo("Bulwark Ox", 7, Rarity.RARE, mage.cards.b.BulwarkOx.class));
        cards.add(new SetCardInfo("Burner Rocket", 114, Rarity.COMMON, mage.cards.b.BurnerRocket.class));
        cards.add(new SetCardInfo("Burnout Bashtronaut", 115, Rarity.RARE, mage.cards.b.BurnoutBashtronaut.class));
        cards.add(new SetCardInfo("Caelorna, Coral Tyrant", 40, Rarity.UNCOMMON, mage.cards.c.CaelornaCoralTyrant.class));
        cards.add(new SetCardInfo("Camera Launcher", 232, Rarity.COMMON, mage.cards.c.CameraLauncher.class));
        cards.add(new SetCardInfo("Caradora, Heart of Alacria", 195, Rarity.RARE, mage.cards.c.CaradoraHeartOfAlacria.class));
        cards.add(new SetCardInfo("Chandra, Spark Hunter", 116, Rarity.MYTHIC, mage.cards.c.ChandraSparkHunter.class));
        cards.add(new SetCardInfo("Clamorous Ironclad", 117, Rarity.COMMON, mage.cards.c.ClamorousIronclad.class));
        cards.add(new SetCardInfo("Cloudspire Captain", 9, Rarity.UNCOMMON, mage.cards.c.CloudspireCaptain.class));
        cards.add(new SetCardInfo("Cloudspire Coordinator", 196, Rarity.UNCOMMON, mage.cards.c.CloudspireCoordinator.class));
        cards.add(new SetCardInfo("Collision Course", 10, Rarity.COMMON, mage.cards.c.CollisionCourse.class));
        cards.add(new SetCardInfo("Count on Luck", 118, Rarity.RARE, mage.cards.c.CountOnLuck.class));
        cards.add(new SetCardInfo("Country Roads", 253, Rarity.UNCOMMON, mage.cards.c.CountryRoads.class));
        cards.add(new SetCardInfo("Crash and Burn", 119, Rarity.COMMON, mage.cards.c.CrashAndBurn.class));
        cards.add(new SetCardInfo("Cryptcaller Chariot", 80, Rarity.RARE, mage.cards.c.CryptcallerChariot.class));
        cards.add(new SetCardInfo("Daretti, Rocketeer Engineer", 120, Rarity.RARE, mage.cards.d.DarettiRocketeerEngineer.class));
        cards.add(new SetCardInfo("Daring Mechanic", 11, Rarity.COMMON, mage.cards.d.DaringMechanic.class));
        cards.add(new SetCardInfo("Deathless Pilot", 82, Rarity.COMMON, mage.cards.d.DeathlessPilot.class));
        cards.add(new SetCardInfo("Debris Beetle", 199, Rarity.RARE, mage.cards.d.DebrisBeetle.class));
        cards.add(new SetCardInfo("Defend the Rider", 157, Rarity.UNCOMMON, mage.cards.d.DefendTheRider.class));
        cards.add(new SetCardInfo("Detention Chariot", 12, Rarity.UNCOMMON, mage.cards.d.DetentionChariot.class));
        cards.add(new SetCardInfo("Dismal Backwater", 254, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("District Mascot", 158, Rarity.RARE, mage.cards.d.DistrictMascot.class));
        cards.add(new SetCardInfo("Diversion Unit", 41, Rarity.UNCOMMON, mage.cards.d.DiversionUnit.class));
        cards.add(new SetCardInfo("Draconautics Engineer", 121, Rarity.RARE, mage.cards.d.DraconauticsEngineer.class));
        cards.add(new SetCardInfo("Dracosaur Auxiliary", 122, Rarity.UNCOMMON, mage.cards.d.DracosaurAuxiliary.class));
        cards.add(new SetCardInfo("Dredger's Insight", 159, Rarity.UNCOMMON, mage.cards.d.DredgersInsight.class));
        cards.add(new SetCardInfo("Dynamite Diver", 123, Rarity.COMMON, mage.cards.d.DynamiteDiver.class));
        cards.add(new SetCardInfo("Earthrumbler", 160, Rarity.UNCOMMON, mage.cards.e.Earthrumbler.class));
        cards.add(new SetCardInfo("Embalmed Ascendant", 201, Rarity.UNCOMMON, mage.cards.e.EmbalmedAscendant.class));
        cards.add(new SetCardInfo("Endrider Catalyzer", 124, Rarity.COMMON, mage.cards.e.EndriderCatalyzer.class));
        cards.add(new SetCardInfo("Endrider Spikespitter", 125, Rarity.UNCOMMON, mage.cards.e.EndriderSpikespitter.class));
        cards.add(new SetCardInfo("Engine Rat", 84, Rarity.COMMON, mage.cards.e.EngineRat.class));
        cards.add(new SetCardInfo("Fang Guardian", 162, Rarity.UNCOMMON, mage.cards.f.FangGuardian.class));
        cards.add(new SetCardInfo("Fearless Swashbuckler", 204, Rarity.RARE, mage.cards.f.FearlessSwashbuckler.class));
        cards.add(new SetCardInfo("Flood the Engine", 42, Rarity.COMMON, mage.cards.f.FloodTheEngine.class));
        cards.add(new SetCardInfo("Forest", 289, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Roads", 255, Rarity.UNCOMMON, mage.cards.f.FoulRoads.class));
        cards.add(new SetCardInfo("Fuel the Flames", 126, Rarity.UNCOMMON, mage.cards.f.FuelTheFlames.class));
        cards.add(new SetCardInfo("Gallant Strike", 13, Rarity.UNCOMMON, mage.cards.g.GallantStrike.class));
        cards.add(new SetCardInfo("Gas Guzzler", 85, Rarity.RARE, mage.cards.g.GasGuzzler.class));
        cards.add(new SetCardInfo("Gastal Blockbuster", 128, Rarity.COMMON, mage.cards.g.GastalBlockbuster.class));
        cards.add(new SetCardInfo("Gastal Raider", 86, Rarity.UNCOMMON, mage.cards.g.GastalRaider.class));
        cards.add(new SetCardInfo("Gastal Thrillroller", 129, Rarity.RARE, mage.cards.g.GastalThrillroller.class));
        cards.add(new SetCardInfo("Gastal Thrillseeker", 205, Rarity.UNCOMMON, mage.cards.g.GastalThrillseeker.class));
        cards.add(new SetCardInfo("Gearseeker Serpent", 43, Rarity.COMMON, mage.cards.g.GearseekerSerpent.class));
        cards.add(new SetCardInfo("Gilded Ghoda", 130, Rarity.COMMON, mage.cards.g.GildedGhoda.class));
        cards.add(new SetCardInfo("Glitch Ghost Surveyor", 44, Rarity.COMMON, mage.cards.g.GlitchGhostSurveyor.class));
        cards.add(new SetCardInfo("Gloryheath Lynx", 14, Rarity.UNCOMMON, mage.cards.g.GloryheathLynx.class));
        cards.add(new SetCardInfo("Goblin Surveyor", 131, Rarity.COMMON, mage.cards.g.GoblinSurveyor.class));
        cards.add(new SetCardInfo("Greasewrench Goblin", 132, Rarity.UNCOMMON, mage.cards.g.GreasewrenchGoblin.class));
        cards.add(new SetCardInfo("Greenbelt Guardian", 164, Rarity.UNCOMMON, mage.cards.g.GreenbeltGuardian.class));
        cards.add(new SetCardInfo("Grim Bauble", 88, Rarity.COMMON, mage.cards.g.GrimBauble.class));
        cards.add(new SetCardInfo("Guardian Sunmare", 15, Rarity.RARE, mage.cards.g.GuardianSunmare.class));
        cards.add(new SetCardInfo("Guidelight Pathmaker", 206, Rarity.UNCOMMON, mage.cards.g.GuidelightPathmaker.class));
        cards.add(new SetCardInfo("Guidelight Synergist", 16, Rarity.UNCOMMON, mage.cards.g.GuidelightSynergist.class));
        cards.add(new SetCardInfo("Haunt the Network", 207, Rarity.UNCOMMON, mage.cards.h.HauntTheNetwork.class));
        cards.add(new SetCardInfo("Haunted Hellride", 208, Rarity.UNCOMMON, mage.cards.h.HauntedHellride.class));
        cards.add(new SetCardInfo("Hazard of the Dunes", 165, Rarity.COMMON, mage.cards.h.HazardOfTheDunes.class));
        cards.add(new SetCardInfo("Hazoret, Godseeker", 133, Rarity.MYTHIC, mage.cards.h.HazoretGodseeker.class));
        cards.add(new SetCardInfo("Hour of Victory", 91, Rarity.UNCOMMON, mage.cards.h.HourOfVictory.class));
        cards.add(new SetCardInfo("Howler's Heavy", 46, Rarity.COMMON, mage.cards.h.HowlersHeavy.class));
        cards.add(new SetCardInfo("Howlsquad Heavy", 134, Rarity.RARE, mage.cards.h.HowlsquadHeavy.class));
        cards.add(new SetCardInfo("Hulldrifter", 47, Rarity.COMMON, mage.cards.h.Hulldrifter.class));
        cards.add(new SetCardInfo("Interface Ace", 17, Rarity.COMMON, mage.cards.i.InterfaceAce.class));
        cards.add(new SetCardInfo("Intimidation Tactics", 92, Rarity.UNCOMMON, mage.cards.i.IntimidationTactics.class));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jibbirik Omnivore", 166, Rarity.COMMON, mage.cards.j.JibbirikOmnivore.class));
        cards.add(new SetCardInfo("Jungle Hollow", 256, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kalakscion, Hunger Tyrant", 93, Rarity.UNCOMMON, mage.cards.k.KalakscionHungerTyrant.class));
        cards.add(new SetCardInfo("Keen Buccaneer", 48, Rarity.COMMON, mage.cards.k.KeenBuccaneer.class));
        cards.add(new SetCardInfo("Kickoff Celebrations", 135, Rarity.COMMON, mage.cards.k.KickoffCelebrations.class));
        cards.add(new SetCardInfo("Lagorin, Soul of Alacria", 211, Rarity.UNCOMMON, mage.cards.l.LagorinSoulOfAlacria.class));
        cards.add(new SetCardInfo("Leonin Surveyor", 18, Rarity.COMMON, mage.cards.l.LeoninSurveyor.class));
        cards.add(new SetCardInfo("Lightning Strike", 136, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Lightshield Parry", 19, Rarity.COMMON, mage.cards.l.LightshieldParry.class));
        cards.add(new SetCardInfo("Lightwheel Enhancements", 20, Rarity.COMMON, mage.cards.l.LightwheelEnhancements.class));
        cards.add(new SetCardInfo("Locust Spray", 95, Rarity.UNCOMMON, mage.cards.l.LocustSpray.class));
        cards.add(new SetCardInfo("Lotusguard Disciple", 21, Rarity.COMMON, mage.cards.l.LotusguardDisciple.class));
        cards.add(new SetCardInfo("Loxodon Surveyor", 167, Rarity.COMMON, mage.cards.l.LoxodonSurveyor.class));
        cards.add(new SetCardInfo("Lumbering Worldwagon", 168, Rarity.RARE, mage.cards.l.LumberingWorldwagon.class));
        cards.add(new SetCardInfo("Magmakin Artillerist", 137, Rarity.COMMON, mage.cards.m.MagmakinArtillerist.class));
        cards.add(new SetCardInfo("Marauding Mako", 138, Rarity.UNCOMMON, mage.cards.m.MaraudingMako.class));
        cards.add(new SetCardInfo("Marketback Walker", 235, Rarity.RARE, mage.cards.m.MarketbackWalker.class));
        cards.add(new SetCardInfo("Marshals' Pathcruiser", 236, Rarity.UNCOMMON, mage.cards.m.MarshalsPathcruiser.class));
        cards.add(new SetCardInfo("Maximum Overdrive", 96, Rarity.COMMON, mage.cards.m.MaximumOverdrive.class));
        cards.add(new SetCardInfo("Memory Guardian", 49, Rarity.UNCOMMON, mage.cards.m.MemoryGuardian.class));
        cards.add(new SetCardInfo("Midnight Mangler", 50, Rarity.COMMON, mage.cards.m.MidnightMangler.class));
        cards.add(new SetCardInfo("Migrating Ketradon", 170, Rarity.COMMON, mage.cards.m.MigratingKetradon.class));
        cards.add(new SetCardInfo("Mindspring Merfolk", 51, Rarity.RARE, mage.cards.m.MindspringMerfolk.class));
        cards.add(new SetCardInfo("Molt Tender", 171, Rarity.UNCOMMON, mage.cards.m.MoltTender.class));
        cards.add(new SetCardInfo("Monument to Endurance", 237, Rarity.RARE, mage.cards.m.MonumentToEndurance.class));
        cards.add(new SetCardInfo("Mountain", 286, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muraganda Raceway", 257, Rarity.RARE, mage.cards.m.MuragandaRaceway.class));
        cards.add(new SetCardInfo("Mutant Surveyor", 98, Rarity.COMMON, mage.cards.m.MutantSurveyor.class));
        cards.add(new SetCardInfo("Nesting Bot", 22, Rarity.UNCOMMON, mage.cards.n.NestingBot.class));
        cards.add(new SetCardInfo("Night Market", 258, Rarity.COMMON, mage.cards.n.NightMarket.class));
        cards.add(new SetCardInfo("Nimble Thopterist", 53, Rarity.COMMON, mage.cards.n.NimbleThopterist.class));
        cards.add(new SetCardInfo("Ooze Patrol", 172, Rarity.UNCOMMON, mage.cards.o.OozePatrol.class));
        cards.add(new SetCardInfo("Outpace Oblivion", 139, Rarity.UNCOMMON, mage.cards.o.OutpaceOblivion.class));
        cards.add(new SetCardInfo("Oviya, Automech Artisan", 173, Rarity.RARE, mage.cards.o.OviyaAutomechArtisan.class));
        cards.add(new SetCardInfo("Pacesetter Paragon", 140, Rarity.UNCOMMON, mage.cards.p.PacesetterParagon.class));
        cards.add(new SetCardInfo("Pactdoll Terror", 99, Rarity.COMMON, mage.cards.p.PactdollTerror.class));
        cards.add(new SetCardInfo("Pedal to the Metal", 141, Rarity.COMMON, mage.cards.p.PedalToTheMetal.class));
        cards.add(new SetCardInfo("Perilous Snare", 23, Rarity.RARE, mage.cards.p.PerilousSnare.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plow Through", 174, Rarity.UNCOMMON, mage.cards.p.PlowThrough.class));
        cards.add(new SetCardInfo("Point the Way", 175, Rarity.UNCOMMON, mage.cards.p.PointTheWay.class));
        cards.add(new SetCardInfo("Possession Engine", 54, Rarity.RARE, mage.cards.p.PossessionEngine.class));
        cards.add(new SetCardInfo("Pride of the Road", 24, Rarity.UNCOMMON, mage.cards.p.PrideOfTheRoad.class));
        cards.add(new SetCardInfo("Prowcatcher Specialist", 142, Rarity.COMMON, mage.cards.p.ProwcatcherSpecialist.class));
        cards.add(new SetCardInfo("Pyrewood Gearhulk", 216, Rarity.MYTHIC, mage.cards.p.PyrewoodGearhulk.class));
        cards.add(new SetCardInfo("Quag Feast", 100, Rarity.RARE, mage.cards.q.QuagFeast.class));
        cards.add(new SetCardInfo("Racers' Scoreboard", 239, Rarity.UNCOMMON, mage.cards.r.RacersScoreboard.class));
        cards.add(new SetCardInfo("Rangers' Aetherhive", 217, Rarity.UNCOMMON, mage.cards.r.RangersAetherhive.class));
        cards.add(new SetCardInfo("Rangers' Refueler", 55, Rarity.UNCOMMON, mage.cards.r.RangersRefueler.class));
        cards.add(new SetCardInfo("Reef Roads", 259, Rarity.UNCOMMON, mage.cards.r.ReefRoads.class));
        cards.add(new SetCardInfo("Regal Imperiosaur", 177, Rarity.RARE, mage.cards.r.RegalImperiosaur.class));
        cards.add(new SetCardInfo("Ride's End", 25, Rarity.COMMON, mage.cards.r.RidesEnd.class));
        cards.add(new SetCardInfo("Ripclaw Wrangler", 101, Rarity.COMMON, mage.cards.r.RipclawWrangler.class));
        cards.add(new SetCardInfo("Rise from the Wreck", 178, Rarity.UNCOMMON, mage.cards.r.RiseFromTheWreck.class));
        cards.add(new SetCardInfo("Risen Necroregent", 102, Rarity.UNCOMMON, mage.cards.r.RisenNecroregent.class));
        cards.add(new SetCardInfo("Risky Shortcut", 103, Rarity.COMMON, mage.cards.r.RiskyShortcut.class));
        cards.add(new SetCardInfo("Riverpyre Verge", 260, Rarity.RARE, mage.cards.r.RiverpyreVerge.class));
        cards.add(new SetCardInfo("Road Rage", 145, Rarity.UNCOMMON, mage.cards.r.RoadRage.class));
        cards.add(new SetCardInfo("Roadside Assistance", 26, Rarity.UNCOMMON, mage.cards.r.RoadsideAssistance.class));
        cards.add(new SetCardInfo("Roadside Blowout", 58, Rarity.UNCOMMON, mage.cards.r.RoadsideBlowout.class));
        cards.add(new SetCardInfo("Rocketeer Boostbuggy", 220, Rarity.UNCOMMON, mage.cards.r.RocketeerBoostbuggy.class));
        cards.add(new SetCardInfo("Rocky Roads", 261, Rarity.UNCOMMON, mage.cards.r.RockyRoads.class));
        cards.add(new SetCardInfo("Rover Blades", 241, Rarity.UNCOMMON, mage.cards.r.RoverBlades.class));
        cards.add(new SetCardInfo("Rugged Highlands", 262, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Run Over", 179, Rarity.COMMON, mage.cards.r.RunOver.class));
        cards.add(new SetCardInfo("Sab-Sunen, Luxa Embodied", 221, Rarity.MYTHIC, mage.cards.s.SabSunenLuxaEmbodied.class));
        cards.add(new SetCardInfo("Sabotage Strategist", 59, Rarity.UNCOMMON, mage.cards.s.SabotageStrategist.class));
        cards.add(new SetCardInfo("Salvation Engine", 27, Rarity.MYTHIC, mage.cards.s.SalvationEngine.class));
        cards.add(new SetCardInfo("Scoured Barrens", 263, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scrap Compactor", 242, Rarity.COMMON, mage.cards.s.ScrapCompactor.class));
        cards.add(new SetCardInfo("Scrounging Skyray", 60, Rarity.UNCOMMON, mage.cards.s.ScroungingSkyray.class));
        cards.add(new SetCardInfo("Shefet Archfiend", 104, Rarity.UNCOMMON, mage.cards.s.ShefetArchfiend.class));
        cards.add(new SetCardInfo("Silken Strength", 180, Rarity.COMMON, mage.cards.s.SilkenStrength.class));
        cards.add(new SetCardInfo("Skybox Ferry", 243, Rarity.COMMON, mage.cards.s.SkyboxFerry.class));
        cards.add(new SetCardInfo("Skycrash", 146, Rarity.UNCOMMON, mage.cards.s.Skycrash.class));
        cards.add(new SetCardInfo("Skystreak Engineer", 61, Rarity.COMMON, mage.cards.s.SkystreakEngineer.class));
        cards.add(new SetCardInfo("Slick Imitator", 62, Rarity.UNCOMMON, mage.cards.s.SlickImitator.class));
        cards.add(new SetCardInfo("Spectral Interference", 63, Rarity.COMMON, mage.cards.s.SpectralInterference.class));
        cards.add(new SetCardInfo("Spell Pierce", 64, Rarity.UNCOMMON, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Spikeshell Harrier", 65, Rarity.UNCOMMON, mage.cards.s.SpikeshellHarrier.class));
        cards.add(new SetCardInfo("Spin Out", 106, Rarity.COMMON, mage.cards.s.SpinOut.class));
        cards.add(new SetCardInfo("Spotcycle Scouter", 30, Rarity.COMMON, mage.cards.s.SpotcycleScouter.class));
        cards.add(new SetCardInfo("Stall Out", 66, Rarity.COMMON, mage.cards.s.StallOut.class));
        cards.add(new SetCardInfo("Stampeding Scurryfoot", 181, Rarity.COMMON, mage.cards.s.StampedingScurryfoot.class));
        cards.add(new SetCardInfo("Starting Column", 244, Rarity.COMMON, mage.cards.s.StartingColumn.class));
        cards.add(new SetCardInfo("Stock Up", 67, Rarity.UNCOMMON, mage.cards.s.StockUp.class));
        cards.add(new SetCardInfo("Streaking Oilgorger", 107, Rarity.COMMON, mage.cards.s.StreakingOilgorger.class));
        cards.add(new SetCardInfo("Sunbillow Verge", 264, Rarity.RARE, mage.cards.s.SunbillowVerge.class));
        cards.add(new SetCardInfo("Sundial, Dawn Tyrant", 31, Rarity.UNCOMMON, mage.cards.s.SundialDawnTyrant.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 265, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Swiftwing Assailant", 32, Rarity.COMMON, mage.cards.s.SwiftwingAssailant.class));
        cards.add(new SetCardInfo("Syphon Fuel", 108, Rarity.COMMON, mage.cards.s.SyphonFuel.class));
        cards.add(new SetCardInfo("Terrian, World Tyrant", 182, Rarity.UNCOMMON, mage.cards.t.TerrianWorldTyrant.class));
        cards.add(new SetCardInfo("The Last Ride", 94, Rarity.MYTHIC, mage.cards.t.TheLastRide.class));
        cards.add(new SetCardInfo("The Speed Demon", 105, Rarity.MYTHIC, mage.cards.t.TheSpeedDemon.class));
        cards.add(new SetCardInfo("Thopter Fabricator", 68, Rarity.RARE, mage.cards.t.ThopterFabricator.class));
        cards.add(new SetCardInfo("Thornwood Falls", 266, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thundering Broodwagon", 225, Rarity.UNCOMMON, mage.cards.t.ThunderingBroodwagon.class));
        cards.add(new SetCardInfo("Ticket Tortoise", 245, Rarity.COMMON, mage.cards.t.TicketTortoise.class));
        cards.add(new SetCardInfo("Trade the Helm", 69, Rarity.UNCOMMON, mage.cards.t.TradeTheHelm.class));
        cards.add(new SetCardInfo("Tranquil Cove", 267, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Transit Mage", 70, Rarity.UNCOMMON, mage.cards.t.TransitMage.class));
        cards.add(new SetCardInfo("Trip Up", 71, Rarity.COMMON, mage.cards.t.TripUp.class));
        cards.add(new SetCardInfo("Tyrox, Saurid Tyrant", 149, Rarity.UNCOMMON, mage.cards.t.TyroxSauridTyrant.class));
        cards.add(new SetCardInfo("Unstoppable Plan", 72, Rarity.RARE, mage.cards.u.UnstoppablePlan.class));
        cards.add(new SetCardInfo("Unswerving Sloth", 34, Rarity.UNCOMMON, mage.cards.u.UnswervingSloth.class));
        cards.add(new SetCardInfo("Valor's Flagship", 35, Rarity.MYTHIC, mage.cards.v.ValorsFlagship.class));
        cards.add(new SetCardInfo("Veloheart Bike", 184, Rarity.COMMON, mage.cards.v.VeloheartBike.class));
        cards.add(new SetCardInfo("Venomsac Lagac", 185, Rarity.COMMON, mage.cards.v.VenomsacLagac.class));
        cards.add(new SetCardInfo("Veteran Beastrider", 226, Rarity.UNCOMMON, mage.cards.v.VeteranBeastrider.class));
        cards.add(new SetCardInfo("Vnwxt, Verbose Host", 73, Rarity.RARE, mage.cards.v.VnwxtVerboseHost.class));
        cards.add(new SetCardInfo("Voyage Home", 227, Rarity.UNCOMMON, mage.cards.v.VoyageHome.class));
        cards.add(new SetCardInfo("Voyager Quickwelder", 37, Rarity.COMMON, mage.cards.v.VoyagerQuickwelder.class));
        cards.add(new SetCardInfo("Walking Sarcophagus", 246, Rarity.COMMON, mage.cards.w.WalkingSarcophagus.class));
        cards.add(new SetCardInfo("Wastewood Verge", 268, Rarity.RARE, mage.cards.w.WastewoodVerge.class));
        cards.add(new SetCardInfo("Waxen Shapethief", 74, Rarity.RARE, mage.cards.w.WaxenShapethief.class));
        cards.add(new SetCardInfo("Wild Roads", 269, Rarity.UNCOMMON, mage.cards.w.WildRoads.class));
        cards.add(new SetCardInfo("Willowrush Verge", 270, Rarity.RARE, mage.cards.w.WillowrushVerge.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 271, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wreck Remover", 247, Rarity.COMMON, mage.cards.w.WreckRemover.class));
        cards.add(new SetCardInfo("Wreckage Wickerfolk", 110, Rarity.COMMON, mage.cards.w.WreckageWickerfolk.class));
        cards.add(new SetCardInfo("Wretched Doll", 111, Rarity.UNCOMMON, mage.cards.w.WretchedDoll.class));
        cards.add(new SetCardInfo("Zahur, Glory's Past", 229, Rarity.RARE, mage.cards.z.ZahurGlorysPast.class));
    }
}
