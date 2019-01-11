**ALIENSHOOTER GUI**

**1.Project Structure**

**1.1.File Structure of JavaFX project**

Inside the project source file; 

We have controller folder for the controllers of static scenes, such as login, register,weekly_scoreboard,alltime_scoreboard and mainmenu.

Fxml folder to store the templates of login,register,weekly_scoreboard,alltime_scorebord and mainmenu

Scoreboard user folder for keeping the User object we take from server via http requests.

And Game folder is the core of the game.

**1.2.Classes inside the game**

All the classes, aliens,players,bullets etc. are derived from fxml shape **circle**. Inside the Particule folder, we have classes like Alien,Boss_Plekumat,Boss_Plekumat_Bullet etc.

The crucial thing here is, all the classes inside this folder is derived from circle.So inside the game, we have circles having different colours and radius. 

**Particule** class is our main abstract class, it extends class **Circle** and holds the radius value and has a very important method for the project, which is **todo**.

**1.2.1. Job Of todo()**

todo() method is very important in the game, since we are using timelines, all the particules need to do something during the game. This abstract **todo()** method is for this purpose. This method is implemented inside the classes which extend **particule** class.

**1.2.2. Live_Being Class**

Live_Being class is another abstract class which is for the implementation of Aliens and Players. It stores maximum health and current health only and it has a radius since it is a particule also.

**1.2.3. Alien Class**

Alien class is an abstract class and it extends Living_Being class. It stores **deltaX** for the horizontal speed of aliens and **value** is for how much score is going to be earned when this alien dies. It also have a direction method for changing the horizontal way of the alien during the game.

In Alien class, we added move() method in order to determine how aliens move in each tick of the timeline.

    public void move() {
        if(!direction) {
            if(getCenterX()-getRadius() >= deltaX) {
                setCenterX(getCenterX()-deltaX);
            }
            else {
                direction = true;
            }
        }
        else {
            if(getCenterX() + getRadius() < 1200 - deltaX) {
                setCenterX(getCenterX() + deltaX);
            }
            else {
                direction = false;

            }
        }
    }
    
It simply changes direction when sees a border.

**1.2.4. Boss Plekumat and Plekumat Classes**

Those classes are the two types of aliens, boss plekumats are stronger than plekumats and boss plekumats are rare in easy levels. They are derived from Alien class, and each tick they are being moved and they fire bullets.

Firing mechanism depends on the luck of player, if the player is lucky enough, they fire just downwards(-y direction), if the player is not lucky, they calculate the angle needed to send bullet to the player and fire the bullets. Boss plekumats are bigger than plekumats, their bullets and value are also bigger than the plekumats.

**1.2.5. Bullet, Player_Bullet, Boss_Plekumat_Bullet and Izmit_Pismaniye classes**

Bullet is the main class for all other derived bullets. Player bullets are blue, Boss_Plekumat_Bullet and Plekumat_Bullets are red and Izmit_Pismaniye is green.

Player and Alien Bullets work as expected. Izmit_Pismaniye is a bullet for strengthening the player during the game. They appear from nowhere and start moving downwards. If the player takes one of these pismaniyes, its health is restored and weapon is upgraded. There are 4 upgrade levels for the player and in each level, number of bullets they produce and the damages of the bullets increase. However if they are being hit by Boss_Plekumat's bullets, then their upgrade level of weapons are decreased.

**1.2.6. Player Class**

Player extends Live_Being class has multiple attributes like left,right,up,down for determining the move input taken from the keyboard, and left_drag,right_drag,up_drag and down_drag for the move characteristics of the player. We also have one_trap_fire to take control of the extensive use of firing(spraying) during the game and upgrade_level for the weapon's condition.

**1.2.6.1. Move Characteristics**

Players are being dragged to the direction they decide to move during the game. This is added to give a more space like game experience. 

change_drag() method changes the drag values of the player and move() is changing the location of player in each tick of the timeline.

**1.2.6.2. Fire Characteristics**

Player also has a fire_bullet() method, it simply checks the level of the weapon and fires accordingly, if the level is higher the amount of bullets and their damages are also larger.

Additionaly, I added another feature for fun. It is Newton's third law(action-reaction). Whenever player fires a bullet, it applies a force in the opposite direction of the player and drags them downwards. **So, the player must be aware of that**.
 
**1.2.7. Game Engine**

Game Engine does the whole work here. It stores the scene,user,stage and a Game_Pane. It has three different timelines names game_timeline, plekumat_fire and boss_plekumat_fire.

It has different array lists;

particules list is for putting every entity inside the game in a loop for controlling them

aliens list is also for a similar purpose and bullets also.

initially_added_aliens_per_level list if for adding different aliens per level.

particules_added and particules_removed lists are for queuing the removed and added objects since we cannot add those objects while we are looping inside the timeline. The particules inside those arraylists are used at the end of the timeline ticks accordingly.

Game engine plays **G.O.R.A Anthem** during the game and sound can be turned off it is annoying.


During the game in the timelines;

In game_timeline, we loop all the particule

If all the aliens are slain, then we save the score of the player by sending the **user** object by turning it into a **Json Object** using **userToJsonstring()** method implemented by me and initiate the next level.Currently, we have 4 levels available but all of these four levels are single player. For the 4th phase of the project, 4th level is going to be changed.

Inside the loop we also check the healths of aliens to find out whether they are dead or not, and check the player's life, if the player is dead, **game over**. If we want to **restart** the game, we press **R** from keyboard and it restarts the game with a clean state.

We also loop the bullets and check for collisions. For example ;

                if(bullet instanceof Plekumat_Bullet) {

                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                        if(score.get() <= 0) {
                            score.set(0);
                        }
                        else {
                            score.set(score.get() - 2);
                        }

                        remove_queue(bullet);
                    }

                }
                
                
This code checks whether plekumat bullet has been hit to the player.It is seen that score of the player is also decreased by **2** if it has been hit. 

And the other thing we check during the game_timeline is spawning aliens and pismaniyes, it is done with this piece of code;

            if(Math.random() < 0.002) {
                
                Random randx = new Random();
                double randomX = 6 + (1200 - 6) * randx.nextDouble();

                Random randy = new Random();
                double randomY = 6 + (250 - 6) * randy.nextDouble();

                Izmit_Pismaniye pismaniye  = new Izmit_Pismaniye(randomX,randomY);
                add_queue(pismaniye);

            }

            if(Math.random() < 0.003) {

                int init_size = initially_added_aliens_per_level.size();
                if(init_size > 0) {
                    Random new_random = new Random();
                    int random_ind = new_random.nextInt(init_size);
                    Alien enemy = initially_added_aliens_per_level.get(random_ind);
                    add_queue(enemy);
                    initially_added_aliens_per_level.remove(random_ind);
                }


            }

Pismaniyes and Aliens are spawned like this.

plekumat_fire and boss_plekumat fire timelines are just for looping all the aliens and making them fire to the player. 


**2.Playing The Game**

The document about playing the game is [here](/gui_document.pdf)