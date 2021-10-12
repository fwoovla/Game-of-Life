import pygame
import random

FPS = 20
width = 500
height = 500
cellsize = 5
alive = 1
dead = 0
last_x = int(width / cellsize)
last_y = int(height / cellsize)

# initialize new array
current_cells = [[random.randint(0, 1) for i in range(last_x)] for j in range(last_y)]

pygame.init()
screen = pygame.display.set_mode([width, height])
clock = pygame.time.Clock()


def drawGrid():
    w = last_x
    for x in range(w):
        pygame.draw.line(screen, (100, 100, 100), (x * cellsize, 0), (x * cellsize, height), 1)
    h = last_y
    for y in range(h):
        pygame.draw.line(screen, (100, 100, 100), (0, y * cellsize), (width, y * cellsize), 1)


def drawCells():
    for x in range(last_x):
        for y in range(last_y):
            if current_cells[x][y] == alive:
                pygame.draw.rect(screen, (0, 255, 0), (x * cellsize, y * cellsize, cellsize, cellsize), 0)


def generateCells():

    new_cells = [[0 for i in range(last_x)] for j in range(last_y)]
    for y in range(1, last_x - 1):
        for x in range(1, last_y - 1):
            neighbors = (current_cells[x - 1][y - 1] + current_cells[x][y - 1] + current_cells[x + 1][y - 1] +
                         current_cells[x - 1][y] + current_cells[x + 1][y] +
                         current_cells[x - 1][y + 1] + current_cells[x][y + 1] + current_cells[x + 1][y + 1])

            if current_cells[x][y] == dead:
                # print("dead cell")
                if neighbors == 3:
                    new_cells[x][y] = alive
                    # print("born")
                    pygame.draw.rect(screen, (155, 155, 155), (x * cellsize, y * cellsize, cellsize, cellsize), 0)

            if current_cells[x][y] == alive:
                # print("alive")
                if neighbors >= 2 and neighbors <= 3:
                    new_cells[x][y] = alive
                    # print("survive")
                    pygame.draw.rect(screen, (0, 155, 0), (x * cellsize, y * cellsize, cellsize, cellsize), 0)

                if neighbors < 2:
                    new_cells[x][y] = dead
                    # print("die")
                    pygame.draw.rect(screen, (100, 0, 0), (x * cellsize, y * cellsize, cellsize, cellsize), 0)
                if neighbors > 3:
                    new_cells[x][y] = dead
                    # print("die")
                    pygame.draw.rect(screen, (200, 0, 0), (x * cellsize, y * cellsize, cellsize, cellsize), 0)

    return new_cells.copy()


# main loop
frame = 0
running = True
while running:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # render()
    screen.fill((20, 20, 20))
    drawGrid()
    current_cells = generateCells()
    pygame.display.flip()
    # print(frame)
    frame += 1
    clock.tick(FPS)

pygame.quit()
