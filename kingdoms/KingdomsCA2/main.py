import pygame
import random

width = 200
height = 200
cell_size = 4
last_x = int(width / cell_size)
last_y = int(height / cell_size)
FPS = 20

red = (255, 0, 0)
green = (0, 255, 0)
blue = (0, 0, 255)
colors = [red, green, blue]

class Creature:
    def __init__(self, color, home, ID):
        self.home = home
        self.ID = ID
        self.x = random.randint(home[0] - 5, home[0] + 5)
        self.y = random.randint(home[1] - 5, home[1] + 5)
        self.color = color
        self.health = random.randint(20, 100)
        self.strength = random.randint(20, 100)

    def move(self):
        # reset velocities
        velX = 0
        velY = 0

        # chose random direction
        dir = random.randint(0, 3)
        if dir == 0: # up
            velY = -1
        if dir == 1: # down
            velY = 1
        if dir == 2: # left
            velX = -1
        if dir == 3: # right
            velX = 1

        self.x += velX
        self.y += velY

        if self.x > last_x:
            self.x = 0
        if self.x < 0:
            self.x = last_x
        if self.y > last_y:
            self.y = 0
        if self.y < 0:
            self.y = last_y

    def draw(self):
        pygame.draw.rect(screen, self.color, (self.x * cell_size, self.y * cell_size, cell_size, cell_size), 0)


class World:
    def __init__(self, num_c, num_pop):
        self.number_of_clans = num_c
        self.number_in_pop = num_pop
        self.population = []

        self.id_num = 0
        for p in range(self.number_of_clans):
            home = (random.randint(0, last_x), random.randint(0, last_y))
            for c in range(self.number_in_pop):
                self.population.append(Creature(colors[p], home, self.id_num))
                self.id_num += 1


    def tickWorld(self):

        new_population = self.population.copy()

        for creature in self.population:
            creature.health += 1
            creature.strength -= 2
            for other in self.population:

                if creature.x == other.x and creature.y == other.y:
                    if creature.ID == other.ID:
                        pass

                    elif creature.health >= 80:
                        chance = random.randint(0, 40)
                        if chance == 1:
                            self.id_num += 1
                            # print("bump same")
                            new_population.append(Creature(creature.color, creature.home, self.id_num))
                            # print("id number: " + str(self.id_num))
                    # if creature.health > 110:
                    #     chance = random.randint(0, 100)
                    #     if chance == 1:
                    #         for c in new_population:
                    #             if c.ID == creature.ID:
                    #                 new_population.remove(creature)

                    chance = random.randint(0, 200)
                    if chance == 1:
                        for c in new_population:
                            if c.ID == creature.ID:
                                new_population.remove(creature)

                    elif creature.color != other.color:
                        #print("bump other")
                        if creature.strength < other.strength:
                            # print("remove self")
                            for c in new_population:
                                if c.ID == creature.ID:
                                    # print("removing")
                                    new_population.remove(c)

                        if creature.strength > other.strength:
                            for c in new_population:
                                if c.ID == other.ID:
                                    new_population.remove(other)
        # for creature in new_population:
        #     for other in new_population:
        #         if creature.x == other.x and creature.y == other.y:
        #             if creature.ID == other.ID:
        #                 pass
        #                 #print("same")
        #             else:
        #                 print("bump")
        #                 if creature.health > other.health:
        #                     new_population.remove(other)
        #                     print("other die")
        #                 elif creature.health < other.health:
        #                     new_population.remove(creature)
        #                     print("creature die")
                #print("id: " + str(other.ID))
            #print("id: " + str(creature.ID))

        self.population = new_population.copy()

        for creature in new_population:
            creature.move()



    def renderWorld(self):
        for creature in self.population:
            creature.draw()

def main():
    num_clans = 3
    clan_info = []
    world = World(num_clans, 200)

    frame = 0
    running = True
    while running:
        screen.fill((0, 0, 0))
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        world.tickWorld()
        world.renderWorld()
        print(len(world.population))
        pygame.display.flip()

        print("frame: " + str(frame))

        for c in range(num_clans):
            clan_info.append(0)

        for c in world.population:
            if c.color == red:
                clan_info[0] += 1
            if c.color == green:
                clan_info[1] += 1
            if c.color == blue:
                clan_info[2] +=1

        print(clan_info)
        clan_info.clear()
        frame += 1
        clock.tick(FPS)


if __name__ == '__main__':
    pygame.init()
    screen = pygame.display.set_mode((width, height))
    clock = pygame.time.Clock()
    main()

