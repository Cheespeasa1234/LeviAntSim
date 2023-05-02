# LeviAntSim
Basic boids / ants simulation. just for fun. will work on it after AP test

# What are Boids?
Boids are simulated objects, that move around in a random direction. All boids must follow these simple rules:
## Separation: Avoid colliding with any other boids
As a boid travels around, it must avoid colliding with any other boids nearby. This typically means that boids will swerve to avoid each other when detected from short distances, or will slightly adjust course when detected from long distances, provided a distance factor is added to the rotation adjustment algorithm.
In this implementation, boids travel in their random direction. When a boid passes within their radius of detection, and other the boid is in front of them (ΔR < 2π), the boid will turn to avoid them. This is done by taking the direction away from the other boid (-1 to 1), and multiplying it by the introvertedness (static constant) and the urgency (1-dist/viewdist). Adding this value to the boid's current rotation will cause it to swerve to avoid in emergencies, and slightly adjust course when it is necessary.
## Alignment: Align yourself with close-by boids
As a boid avoids other boids (nice rhyme), it must align its course with nearby boids. This means, if it is not in danger of colliding with a boid any time soon, it should turn close to parallel to other boids.
## Cohesion: Turn to the center of your current flock
As a boid dances with other boids, it must turn towards the center of the nearby flock. This means that boids will try to stay close together, but will refuse to get too close.
A boid exists in 2d or 3d space, and when all the boids follow these simple rules, they appear to exibit flocking / herding / swarming behaviors. Boid simulations are a type of simulation called an "Emergent Simulation". This is when a complicated behavior "emerges" from simple instructions.

# Resources
- [Sebastian Lague: Coding Adventures - Boids](https://www.youtube.com/watch?v=bqtqltqcQhw)
- [Sebastian Lague: Coding Adventures - Ant and Slime Simulations](https://youtu.be/X-iSQQgOd1A)
- [Sebastian Lague: Complex Behavior from Simple Rules: 3 Simulations](https://youtu.be/kzwT3wQWAHE_)
- [Seb O: Emergent Behavior Simulations](https://projects.thinkglobalschool.org/emergent-behavior-simulations/)
- [Pezza's Work: C++ Ants Simulation 1, First Approach](https://youtu.be/81GQNPJip2Y)
- [dante: How do Boids Work? A Flocking Simulation](https://www.youtube.com/watch?v=QbUPfMXXQIY)
