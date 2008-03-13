(*

category:      Test
synopsis:      Basic two reactions with four species in one 1D compartment
               and one functionDefinition.
componentTags: Compartment, Species, Reaction, Parameter, FunctionDefinition 
testTags:      InitialAmount, 1D-Compartment
testtype:      TimeCourse
levels:        2.1, 2.2, 2.3

The model contains one compartment named compartment.
  There are four species named S1, S2, S3 and S4 and two parameters named k1 and k2.
  Compartment compartment is 1-dimensional.
  The model contains two reactions defined as:
[| | Reaction |||||| Rate |
 | | S1+S2 -> S3+S4 |||||| $multiply(k1,multiply(S1,S2))*compartment$  |
 | | S3+S4 -> S1+S2 |||||| $k2*S3*S4*compartment$  |]


The model contains one functionDefinition defined as:
[|| Id      | Arguments || Formula |
 || multiply | x, y || $x*y$ |]


The initial conditions are as follows:
[|                                  ||          Value  || Units                     |
|              Initial amount of S1:|| $1.0 \x 10^-15$ || mole                      |
|              Initial amount of S2:|| $1.0 \x 10^-15$ || mole                      |
|              Initial amount of S3:|| $2.0 \x 10^-15$ || mole                      |
|              Initial amount of S4:|| $1.0 \x 10^-15$ || mole                      |
|             Value of parameter k1:|| $0.75 \x 10^15$ || metre mole^-1^ second^-1^ |
|             Value of parameter k2:|| $0.25 \x 10^15$ || metre mole^-1^ second^-1^ |
| Length of compartment compartment:|| $            1$ || metre                     |]

The species values are given as amounts of substance to make it easier to
use the model in a discrete stochastic simulator, but (as per usual SBML
principles) they must be treated as concentrations where they appear in
expressions.

*)

newcase[ "00102" ];

addFunction[ multiply, arguments -> {x, y}, math -> x*y];
addCompartment[ compartment, spatialDimensions-> 1, size -> 1 ];
addSpecies[ S1, initialAmount -> 1.0 10^-15];
addSpecies[ S2, initialAmount -> 1.0 10^-15];
addSpecies[ S3, initialAmount -> 2.0 10^-15];
addSpecies[ S4, initialAmount -> 1.0 10^-15];
addParameter[ k1, value -> 0.75 10^15 ];
addParameter[ k2, value -> 0.25 10^15 ];
addReaction[ S1+S2 -> S3+S4, reversible -> False,
	     kineticLaw -> multiply[k1,multiply[S1,S2]]*compartment ];
addReaction[ S3+S4 -> S1+S2, reversible -> False,
	     kineticLaw -> k2*S3*S4*compartment ];

makemodel[]
