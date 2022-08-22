/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.utils;

import bombchicken.gameobj.Spike;

/**
 *
 * @author User
 */
public class Path {

    public static abstract class Flow {

        private String path;

        public Flow(String path) {
            this.path = path;
        }

        public Flow(Flow flow, String path) {
            this.path = flow.path + path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    private static class Resources extends Flow { // 0

        private Resources() {
            super("/resources");
        }
    }

    public static class Imgs extends Flow {  //  1

        private Imgs() {
            super(new Resources(), "/imgs");
        }

        public static class Actors extends Flow { // 2

            private Actors(Flow flow) {
                super(flow, "/actors");
            }

            public static class Chicken extends Flow {  // 3

                private Chicken(Flow flow) {
                    super(flow, "/chicken");
                }

                public String still() {  // 4
                    return this + "/still.png";
                }

                public String walk() {  // 4
                    return this + "/walk.png";
                }

                public String layBomb() {  // 4
                    return this + "/layBomb.png";
                }

                public String hitTop() {  // 4
                    return this + "/hitTop.png";
                }

                public String drop() {  // 4
                    return this + "/drop.png";
                }

                public String dead() {  // 4
                    return this + "/dead.png";
                }

                public String birth() {  // 4
                    return this + "/birth.png";
                }

                public String pushBomb() {  // 4
                    return this + "/pushBomb.png";
                }

                public String stillBlack() {  // 4
                    return this + "/still_black.png";
                }

                public String walkBlack() {  // 4
                    return this + "/walk_black.png";
                }

                public String layBombBlack() {  // 4
                    return this + "/layBomb_black.png";
                }

                public String hitTopBlack() {  // 4
                    return this + "/hitTop_black.png";
                }

                public String dropBlack() {  // 4
                    return this + "/drop_black.png";
                }

                public String deadBlack() {  // 4
                    return this + "/dead_black.png";
                }

                public String birthBlack() {  // 4
                    return this + "/birth_black.png";
                }

                public String pushBombBlack() {  // 4
                    return this + "/pushBomb_black.png";
                }

                public String stillYellow() {  // 4
                    return this + "/still_yellow.png";
                }

                public String walkYellow() {  // 4
                    return this + "/walk_yellow.png";
                }

                public String layBombYellow() {  // 4
                    return this + "/layBomb_yellow.png";
                }

                public String hitTopYellow() {  // 4
                    return this + "/hitTop_yellow.png";
                }

                public String dropYellow() {  // 4
                    return this + "/drop_yellow.png";
                }

                public String deadYellow() {  // 4
                    return this + "/dead_yellow.png";
                }

                public String birthYellow() {  // 4
                    return this + "/birth_yellow.png";
                }

                public String pushBombYellow() {  // 4
                    return this + "/pushBomb_yellow.png";
                }

                public String stillPink() {  // 4
                    return this + "/still_pink.png";
                }

                public String walkPink() {  // 4
                    return this + "/walk_pink.png";
                }

                public String layBombPink() {  // 4
                    return this + "/layBomb_pink.png";
                }

                public String hitTopPink() {  // 4
                    return this + "/hitTop_pink.png";
                }

                public String dropPink() {  // 4
                    return this + "/drop_pink.png";
                }

                public String deadPink() {  // 4
                    return this + "/dead_pink.png";
                }

                public String birthPink() {  // 4
                    return this + "/birth_pink.png";
                }

                public String pushBombPink() {  // 4
                    return this + "/pushBomb_pink.png";
                }

            }
            
            public static class Worker extends Flow {  // 3

                private Worker(Flow flow) {
                    super(flow, "/worker");
                }

                public String hammer() {  // 4
                    return this + "/worker_idle_hammering.png";
                }
                
                public String phoneR() {  // 4
                    return this + "/worker_idle_phone_r.png";
                }
                
                public String phoneL() {  // 4
                    return this + "/worker_idle_phone_l.png";
                }
                
                public String swab() {  // 4
                    return this + "/worker_idle_swabbing.png";
                }
                
                public String sauce() {  // 4
                    return this + "/worker_sauce_eat.png";
                }
            }
            
            public static class Dead extends Flow {  // 3

                private Dead(Flow flow) {
                    super(flow, "/dead");
                }
                
                public String dead() {  // 4
                    return this + "/dead.png";
                }
            }
            
            public static class Pig extends Flow {  // 3

                private Pig(Flow flow) {
                    super(flow, "/pig");
                }
                
                public String flappyPigRight() {  // 4
                    return this + "/flappyPigRight.png";
                }
                
                public String flappyPigLeft() {  // 4
                    return this + "/flappyPigLeft.png";
                }
            }
            
            public static class FriedChicken extends Flow {  // 3

                private FriedChicken(Flow flow) {
                    super(flow, "/friedChicken");
                }
                
                public String look() {  // 4
                    return this + "/chicken_look.png";
                }
                public String walk() {  // 4
                    return this + "/chicken_walk.png";
                }
                public String peck() {  // 4
                    return this + "/chicken_peck.png";
                }
                public String lookL() {  // 4
                    return this + "/chicken_look_l.png";
                }
                public String walkL() {  // 4
                    return this + "/chicken_walk_l.png";
                }
                public String peckL() {  // 4
                    return this + "/chicken_peck_l.png";
                }
                public String roast() {  // 4
                    return this + "/chicken_roast.png";
                }
            }

            public Chicken chicken() {
                return new Chicken(this);
            }
            
            public Worker worker() {
                return new Worker(this);
            }
            
            public Dead dead() {
                return new Dead(this);
            }
            
            public FriedChicken friedChicken() {
                return new FriedChicken(this);
            }
            
            public Pig pig() {
                return new Pig(this);
            }
        }

        public static class Props extends Flow { // 2

            private Props(Flow flow) {
                super(flow, "/props");
            }

            public static class Bomb extends Flow { // 3

                private Bomb(Flow flow) {
                    super(flow, "/bomb");
                }

                public String explode() { // 4
                    return this + "/explode.png";
                }

                public String throb() { // 4
                    return this + "/throb.png";
                }

                public String flyRight() { // 4
                    return this + "/flyRight.png";
                }

                public String flyLeft() { // 4
                    return this + "/flyLeft.png";
                }

                public String bombFire() { // 4
                    return this + "/bombFires.png";
                }

            }

            public static class Spike extends Flow { // 3

                private Spike(Flow flow) {
                    super(flow, "/spike");
                }

                public String shine() { // 4
                    return this + "/shine.png";
                }

                public String backShine() { // 4
                    return this + "/backShine.png";
                }

            }

            public static class Belt extends Flow { // 3

                private Belt(Flow flow) {
                    super(flow, "/belt");
                }

                public String work() { // 4
                    return this + "/work.png";
                }

            }
            
            public static class Flap extends Flow { // 3

                private Flap(Flow flow) {
                    super(flow, "/flap");
                }

                public String flap1() { // 4
                    return this + "/flap1.png";
                }

            }
            
            public static class Pressbtn extends Flow { // 3

                private Pressbtn(Flow flow) {
                    super(flow, "/pressbtn");
                }

                public String pressbtn1() { // 4
                    return this + "/pressbtn1.png";
                }

                public String pressbtn2() { // 4
                    return this + "/pressbtn2.png";
                }
                
                public String pressbtn3() { // 4
                    return this + "/pressbtn3.png";
                }

                public String pressbtn4() { // 4
                    return this + "/pressbtn4.png";
                }

            }

            public static class Gem extends Flow { // 3

                private Gem(Flow flow) {
                    super(flow, "/gem");
                }

                public String gemWall() { // 4
                    return this + "/gemWall.png";
                }

                public String gem() { // 4
                    return this + "/gem.png";
                }

                public String gemLabel() { // 4
                    return this + "/gemLabel.png";
                }

                public String chestOpen() { // 4
                    return this + "/chestOpen.png";
                }

                public String chestClose() { // 4
                    return this + "/chestClose.png";
                }

                public String chestEmpty() { // 4
                    return this + "/chestEmpty.png";
                }

                public String gemGet() { // 4
                    return this + "/gemGet.png";
                }

            }

            public static class Fire extends Flow { // 3

                private Fire(Flow flow) {
                    super(flow, "/fire");
                }

                public String fireFlyLeft() { // 4
                    return this + "/fire_left.png";
                }

                public String fireFlyRight() { // 4
                    return this + "/fire_right.png";
                }

            }

            public Belt belt() {
                return new Belt(this);
            }

            public Spike spike() {
                return new Spike(this);
            }

            public Bomb bomb() {
                return new Bomb(this);
            }

            public Gem gem() {
                return new Gem(this);
            }
            
            public Flap flap() {
                return new Flap(this);
            }
            
            public Pressbtn pressbtn() {
                return new Pressbtn(this);
            }

            public Fire fire() {
                return new Fire(this);
            }
        }

        public static class Backgrounds extends Flow { // 2

            private Backgrounds(Flow flow) {
                super(flow, "/backgrounds");
            }

            public String main() {
                return this + "/main.png";
            }

            public String factory() {
                return this + "/factory.png";
            }

            public String factory2() {
                return this + "/factory2.jpg";
            }
            
            public String background1() {
                return this + "/background1.png";
            }

            public String background2() {
                return this + "/background2.jpg";
            }
            
            public String background3() {
                return this + "/background3.png";
            }

            public String background4() {
                return this + "/background4.jpg";
            }
            
            public String background5() {
                return this + "/background5.png";
            }

            public String background6() {
                return this + "/background6.jpg";
            }

            public String room() {
                return this + "/room.png";
            }

            public String teachPop() {
                return this + "/teachPop.png";
            }

            public String chooseMode() {
                return this + "/chooseMode.png";
            }

            public String chooseMap() {
                return this + "/chooseMap.png";
            }
           
            public String mobaRoom() {
                return this + "/mobaRoom.png";
            }

            public String gameOver() {
                return this + "/gameOver.png";
            }

            public String gameOverPc() {
                return this + "/gameOverPc.png";
            }
             public String black() {
                return this + "/black.png";
            }
             
             

        }

        public static class Objs extends Flow { // 2

            private Objs(Flow flow) {
                super(flow, "/objs");
            }

            public String steel() {
                return this + "/steel.png";
            }

            public String wall1() {
                return this + "/wall1.png";
            }

            public String wall2() {
                return this + "/wall2.png";
            }

            public String brick() {
                return this + "/brick.png";
            }

            public String grass() {
                return this + "/grass.png";
            }

            public String water() {
                return this + "/water.png";
            }

            public String bigchicken() {
                return this + "/bigchicken.png";
            }

            public String elevator() {
                return this + "/elevator.png";
            }

            public String elevatorBlack() {
                return this + "/elevator_black.png";
            }

            public String elevatorPink() {
                return this + "/elevator_pink.png";
            }

            public String elevatorYellow() {
                return this + "/elevator_yellow.png";
            }

            public String chickenButton() {
                return this + "/chickenButton.png";
            }

            public String startWordBlack() {
                return this + "/START.png";
            }

            public String exitWordBlack() {
                return this + "/EXIT.png";
            }

            public String exitChoose() {
                return this + "/exitChoose.png";
            }

            public String lightRing() {
                return this + "/lightRing.png";
            }

            public String IP() {
                return this + "/IP.png";
            }

            public String name() {
                return this + "/name.png";
            }

            public String client() {
                return this + "/client.png";
            }

            public String check() {
                return this + "/check.png";
            }

            public String pounder() { //衝槌
                return this + "/pounder.png";
            }

            public String cannonRight() { //砲台
                return this + "/cannon_right.png";
            }

            public String cannonLeft() { //砲台
                return this + "/cannon_left.png";
            }

            public String signReady() { //指示牌
                return this + "/sign_ready.png";
            }

            public String ball() { //指示牌
                return this + "/ball.png";
            }

            public String blueTower() {
                return this + "/blueTower.png";
            }

            public String redTower() {
                return this + "/redTower.png";
            }

            public String crack() {
                return this + "/crack.png";
            }

            public String floor1() {
                return this + "/floor1.png";
            }

            public String floor2() {
                return this + "/floor2.png";
            }

            public String spike2() {
                return this + "/spike2.png";
            }

            public String yellowSteel() {
                return this + "/yellowSteel.png";
            }

            public String a() {
                return this + "/a.png";
            }

            public String big() {
                return this + "/big.png";
            }

            public String stone() {
                return this + "/stone.png";
            }

            public String blueMark() {
                return this + "/blueMark.png";
            }

            public String redMark() {
                return this + "/redMark.png";
            }

            public String chickenLeg() {
                return this + "/chickenLeg.png";
            }

            public String light() {
                return this + "/light.png";
            }

            public String eat() {
                return this + "/eat.png";
            }

            public String rebirthPoint() {
                return this + "/rebirthPoint.png";
            }
            
            public String rebirthPointD() {
                return this + "/rebirthPoint_dark.png";
            }
            
            public String boxStack2() {
                return this + "/box_stack2.png";
            }

            public String boxStack() {
                return this + "/box_stack2.png";
            }
            
            public String boxesStack1() {
                return this + "/boxes_bg_stack1.png";
            }
            
            public String boxesPrint1() {
                return this + "/boxes_xl_print1.png";
            }
            
            public String boxesPrint2() {
                return this + "/boxes_xl_print1.png";
            }
            
            public String bulkheadSwitch1() {
                return this + "/bulkhead_switch_side1.png";
            }
            
            public String bulkHeadSwitch2() {
                return this + "/bulkhead_switch1.png";
            }
            
            public String ceiling() {
                return this + "/ceiling.png";
            }
            
            
            public String lamp() {
                return this + "/ceiling_lamp_dark.png";
            }
            
            public String coopEggs() {
                return this + "/coop_eggs_light.png";
            }
            
            public String coopGround() {
                return this + "/coop_ground_dark.png";
            }
            
            public String coopGroundL() {
                return this + "/coop_ground_light.png";
            }
            
            public String coopD() {
                return this + "/coop_dark.png";
            }
            
            public String door() {
                return this + "/destructible_door.png";
            }
            
            public String pot() {
                return this + "/destructible_pot3.png";
            }
            
            public String eggs() {
                return this + "/eggs_boxes_piled.png";
            }
            
            public String eggs1() {
                return this + "/eggs_boxes_piled.png";
            }
            
            public String eggs2() {
                return this + "/eggs_intro.png";
            }
            
            public String factoryWall1() {
                return this + "/factory_wall1_dark.png";
            }
            
            public String factoryWall2() {
                return this + "/factory_wall2_dark.png";
            }
            
            public String fan1() {
                return this + "/fan_dark01.png";
            }
            
            public String flap() {
                return this + "/flap_trap_01.png";
            }
            
            public String grass1() {
                return this + "/hay_foreground01.png";
            }
            
            public String halfpipe1() {
                return this + "/halftile_pipe_left_light.png";
            }
            
            public String halfpipe2() {
                return this + "/halftile_pipe_mid1_light.png";
            }
            
            public String halfpipe3() {
                return this + "/halftile_pipe_right_light.png";
            }
            
            public String pressBtn1() {
                return this + "/pressure_button_01.png";
            }
            
            public String wallL() {
                return this + "/skulltile_brick_wall_left.png";
            }
            
            public String wall3() {
                return this + "/skulltile_brick_bottom_middle1_D.png";
            }
            
            public String wall4() {
                return this + "/skulltile_brick_top_right.png";
            }
            
            public String wall5() {
                return this + "/skulltile_brick_right1.png";
            }
            
            public String wall6() {
                return this + "/skulltile_brick_wall_right.png";
            }
            
            public String wall7() {
                return this + "/skulltile_brick_left1.png";
            }
            
            public String corner1() {
                return this + "/skulltile_32block_skull_D.png";
            }
            
            public String bigboxes() {
                return this + "/box_stack2.png";
            }
            
            public String statue1() {
                return this + "/statue_intro.png";
            }
            
            public String corner2() {
                return this + "/skulltile_brick_wall_corner_top_left.png";
            }
            
            public String corner3() {
                return this + "/skulltile_brick_wall_corner_top_right_D.png";
            }
            
            public String corner4() {
                return this + "/skulltile_brick_bottom_right.png";
            }
            
            public String boxStack5() {
                return this + "/box_stack1.png";
            }
            
            public String corner8() {
                return this + "/skulltile_brick_bottom_left_D.png";
            }
            
            public String chest1() {
                return this + "/chest_01.png";
            }
            
            public String wallBrickable1() {
                return this + "/breakable_hidden_big_dark.png";
            }
            
            public String wallstone() {
                return this + "/skulltile_32block_plain.png";
            }
            
            public String wall8() {
                return this + "/factory_tile1_light.png";
            }
            
            public String bulkhead3() {
                return this + "/bulkhead_switch1.png";
            }
            
            public String pipe1() {
                return this + "/transition_pipe_bottom.png";
            }
            
            public String pipe2() {
                return this + "/transition_pipe_bottom.png";
            }
            
            public String pipeMid() {
                return this + "/transition_pipe_mid.png";
            }
            
            public String pipe3() {
                return this + "/transition_pipe_top.png";
            }
            
            public String switch1() {
                return this + "/bulkhead_switch_side1.png";
            }
            
            public String invisitile1() {
                return this + "/invisitile01.png";
            }
            
            public String elevator1() {
                return this + "/teleporter_close01.png";    
            }
            
            public String wallC() {
                return this + "/skulltile_brick_bottom_middle1_D.png";
            }
            
            public String light1() {
                return this + "/light_procreate.png";
            }
            
            public String pressbtnL() {
                return this + "/pressure_button_left.png";
            }
            public String pressbtnR() {
                return this + "/pressure_button_right.png";
            }
            public String pressbtnU() {
                return this + "/pressure_button_up.png";
            }
            public String barrelStack1() {
                return this + "/barrel_stack1_L.png";
            }
            public String barrel1() {
                return this + "/barrel3_L.png";
            }
            
            public String storageSign() {
                return this + "/storage_sign03.png";
            }
            
            public String spiderweb() {
                return this + "/spiderweb_left.png";
            }
            
            public String spiderweb2() {
                return this + "/spiderweb_mid.png";
            }
            
            public String spiderweb3() {
                return this + "/spiderweb_right.png";
            }
            
            public String tile2() {
                return this + "/factory_tile_big1_light.png";
            }
            
            public String tile3() {
                return this + "/factory_tile_big2_light.png";
            }
            
            public String billboard1() {
                return this + "/billboard_freeone1.png";
            }
            
            public String billboard2() {
                return this + "/billboard_addiction1.png";
            }
            
            public String transpipe1() {
                return this + "/transpipe1.png";
            }
            
            public String transpipe2() {
                return this + "/transpipe2.png";
            }
            
            public String toilet2() {
                return this + "/toilet_open2.png";
            }
            
            public String toilet3() {
                return this + "/toilet_broken2.png";
            }
            
            public String toilet() {
                return this + "/toilet2.png";
            }
     
          
        }

        public Actors actors() {
            return new Actors(this);
        }

        public Props props() {
            return new Props(this);
        }

        public Objs objs() {
            return new Objs(this);
        }

        public Backgrounds backgrounds() {
            return new Backgrounds(this);
        }
    }

    public static class Sounds extends Flow { // 1

        private Sounds() {
            super(new Resources(), "/sounds");
        }

        public static class ChickenSound extends Flow { // 2

            private ChickenSound(Flow flow) {
                super(flow, "/chickensound");
            }

            public String bomb_lay() {  // 3
                return this + "/bomb_lay.wav";
            }

            public String chicken_death() {  // 3
                return this + "/chicken_death.wav";
            }

            public String chicken_speech_bubble() {  // 3
                return this + "/chicken_speech_bubble.wav";
            }

            public String headBang() {  // 3
                return this + "/headBang.wav";
            }

            public String kick_bomb1() {  // 3
                return this + "/kick_bomb1.wav";
            }

            public String reborn() {  // 3
                return this + "/reborn.wav";
            }

            public String footstep1() {  // 3
                return this + "/footstep1.wav";
            }

            public String footstep2() {  // 3
                return this + "/footstep2.wav";
            }
        }

        public static class ObjectSound extends Flow { // 2

            private ObjectSound(Flow flow) {
                super(flow, "/objectsound");
            }

            public String explode1() {  // 3
                return this + "/explode1.wav";
            }

            public String explode2() {  // 3
                return this + "/explode2.wav";
            }

            public String rolling_bomb_short() {  // 3
                return this + "/rolling_bomb_short.wav";
            }

            public String breakblock1() {  // 3
                return this + "/breakblock1.wav";
            }

            public String chestOpen() {  // 3
                return this + "/chestOpen.wav";
            }

            public String door_open() {  // 3
                return this + "/door_open.wav";
            }

            public String menu_nav_back() {  // 3
                return this + "/menu_nav_back.wav";
            }

            public String menu_nav_select() {  // 3
                return this + "/menu_nav_select.wav";
            }

            public String secret_revealed() {  // 3
                return this + "/secret_revealed.wav";
            }

            public String title_appear() {  // 3
                return this + "/menu_nav_back.wav";
            }

            public String gemwoosh() {  // 3
                return this + "/gemwoosh.wav";
            }

        }

        public static class BackGroundMusic extends Flow { // 2

            private BackGroundMusic(Flow flow) {
                super(flow, "/backgroundmusic");
            }

            public String Gameover() {  // 3
                return this + "/Gameover.wav";
            }

            public String Menu() {  // 3
                return this + "/Menu.wav";
            }

            public String Temple2() {  // 3
                return this + "/Temple2.wav";
            }

            public String LavaThemeLayer1() {  // 3
                return this + "/Lava Theme Layer 1.wav";
            }

            public String JungleThemeLayer1() {  // 3
                return this + "/Jungle Theme Layer 1.wav";
            }

            public String LavaThemeLayer2() {  // 3
                return this + "/Lava Theme Layer 2.wav";
            }
             public String ending() {  // 3
                return this + "/ending.wav";
            }
             public String chickenAttack() {  // 3
                return this + "/chickenAttack.wav";
            }
        }

        public ChickenSound ChickenSound() {
            return new ChickenSound(this);
        }

        public ObjectSound ObjectSound() {
            return new ObjectSound(this);
        }

        public BackGroundMusic BackGroundMusic() {
            return new BackGroundMusic(this);
        }
    }

    public Imgs img() {
        return new Imgs();
    }

    public Sounds sound() {
        return new Sounds();
    }
}
