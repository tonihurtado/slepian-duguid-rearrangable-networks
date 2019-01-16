# Implementation of Slepian-Duguid theorem in a three-stage rearrangable network.

*Project for the subject Switching & Routing, Politecnico di Milano. MSc in Telecommunication Engineering (Networks)*

Simulation of the 3-stage rearrangeable Slepian-Duguid network. Use of Paul matrix to setup connections and perform rearrangements.
Generation of permutations. Statistics about number of rearrangements and computation complexity.

[Full slides](readme/06-sw_th_rear-snb_2016_19.pdf)

## State of a three-stage network represented by the Paull matrix.

![network](/readme/1.png?raw=true)

* It has r1 rows and r3 columns
* Each matrix entry has up to r2 distinct symbols: 1, 2, ..., r2.
* The symbol a in the matrix entry means that an inlet of the first-stage matrix i is connected to an outlet of the last-stage matrix j through the middlestage matrix a

![matrix](/readme/2.png?raw=true)

### Conditions of the Paull matrix
* At most min(n,r2) distinct symbols per row.
* At most min(r2,m) distinct symbols per column.

### Slepian-Duguid theorem
A 3-stage network is RNB if and only if r2 ≥ max(n,m)

## Example

![example](/readme/3.png?raw=true)
