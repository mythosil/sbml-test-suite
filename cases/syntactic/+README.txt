
              The SBML Test Suite: Syntactic Test Cases

                     Sarah Keating, Lucian Smith,
                    Michael Hucka, Frank Bergmann

            For more information about the SBML Test Suite
        please visit http://sbml.org/Software/SBML_Test_Suite
          or contact the SBML Team at sbml-team@caltech.edu

             Please report problems  using the tracker at
          https://github.com/sbmlteam/sbml-test-suite/issues

    Please join the sbml-interoperability mailing list by visiting
                      http://www.sbml.org/Forums

   ,--------------------------------------------------------------.
  | Table of contents                                             |
  | 1. Introduction                                               |  
  | 2. The origin of these files                                  |
  | 3. Interpretation of folders                                  |
  | 4. Interpretation of file names                               |
  | 5. Interpretation of .txt files                               |
  | 6. Interpretation of the file uniqueErrors.txt                |
  | 7. (Lack of) Integration with the rest of the SBML Test Suite |
  | 8. Licensing and distribution terms                           |
   `--------------------------------------------------------------'


1. INTRODUCTION
======================================================================

The SBML Test Suite is a conformance testing system for SBML. It
allows developers and users to test the degree and correctness of SBML
support provided in an SBML-compatible software package.

This portion of the SBML Test Suite consists of syntactic test cases
only.  These test models are provided for two purposes.  First, they
are here simply to present malformed input for your SBML interpreter,
to ensure that it deals with all contingencies in a graceful manner.
For interpreters that use libSBML and only accept validated models,
that step covers the majority of the cases herein.  However, there are
many models with a variety of warnings that might cause problems for
particular types of simulators -- for example, models with
uninitialized elements are valid SBML, but cannot be simulated without
further input.  And interpreters that accept all SBML models regardless
of that model's validation state should find this suite of models
quite useful in uncovering any hidden assumptions about the models
they interpret.

Second, SBML interpreters can use these files to test the way they
report validation errors and warnings to the user.  Again, if the
interpreter uses libSBML, this will be trivial: the files themselves
were generated automatically with libSBML, after all.  But
interpreters that use their own XML parsers, or which use JSBML, can
use these files to detect differences in how those parsers interpret
valid and invalid SBML models vs. how libSBML interprets those models.


2. THE ORIGIN OF THESE FILES
======================================================================

All the folders in this folder are auto-generated by the program
'createSyntacticTests', whose source code can be found in the
SourceForge.net repository for the SBML Test Suite within in the
subfolder "test-suite/src/utilities/createSyntacticTests".  For
more information on how to compile and run that program, please see
the file "test-suite/src/utilities/createSyntacticTests/README.txt"
(also in the SBML Test Suite repository on SourceForge).


3. INTERPRETATION OF FOLDERS
======================================================================

When viewed from the top level, the cases are located in the subfolder
"cases/syntactic/".

Each folder is named after the validation rule that it tests.
Validation rules that appear in SBML core specifications (starting
from SBML Level 2 Version 2) are simply labeled with the number of the
validation rule.  Validation messages that are not in the SBML core
specification, but are used by libSBML have the numbers in the
80000-99999 range.  Those in the 80000-89999 range are warnings, and
those in the 90000-99999 range are errors.

Validation rules from package specifications are given name and number
combinations that come from each specification: 'comp-xxxxx' rules
come from the Hierarchical Model Composition package, 'fbc-xxxxx'
rules come from the Flux Balance Constraints package, 'layout-xxxxx'
rules come from the Layout package, and 'qual-xxxxx' rules come from
the Qualitative Models package.


4. INTERPRETATION OF FILE NAMES
======================================================================

The format of each test file's name contains information about the
file itself.  Each name follows the following format:

[[pkg-]][rule#]-[pass/fail][[-extrarule#]]-[numfails]-[id#]-sev[#]-l[#]v[#]

Elements in double brackets ('[[]]') are optional; elements in single
brackets are mandatory but vary.

[[pkg-]] : Optional: if the validation rule comes from a package, the
name is prepended to the filename.  This matches the folder name.

[rule#] : The validation rule (identified by its number) being tested.

[pass/fail] : Either "fail", if the model fails the validation rule
being tested, or "pass", if the model passes the validation rule being
tested.

[[-extrarule#]] : Optional: if the model also contains another
error besides the one being tested, that validation error number is
listed here.  If the model has additional validation warnings, those
numbers are *not* listed.

[numfails] : The number of times the model violates the validation
rule being tested.

[id#] : A two-digit number provided to uniquely identify the model
from other models that test the same validation rule in the same way.

sev[#] : "sev0" through "sev3", indicating the severity of the
validation rule being tested.  The vast majority of these are "sev1",
for warnings, or "sev2", for errors.  "sev0" validation messages are
informational only, and "sev3" errors are fatal.

l[#]v[#] : The SBML level and version of the model.  Possible values
are l1v2, l2v1, l2v2, l2v3, l2v4, l2v5, l3v1 and l3v2.


5. INTERPRETATION OF .TXT FILES
======================================================================

Along with each test model (.xml file) is a text file with the same
name, but ending in ".txt" This file contains all the
libSBML-generated error and warning messages for the model.  It is
guaranteed that all of the error messages (if any) will be mentioned
in the filename itself, either as the main validation rule being
tested, or as the 'extra' rule number present in the model.  Each file
may have multiple warning messages, however.  Each message in the file
is demarcated by a line of dashes, and contains information as in this
example from 10404-fail-01-04-sev2-l3v1.txt:

------------------
Validation id    :	10404
Validation number:	10404
Severity         :	Error
Line number      :	17
Package          :	
Short message    :	Only one Annotation object is permitted under
a given SBML object
Full message     :	A given SBML object may contain at most one
<annotation> element.  Reference: L3V1 Section 3.2.  An SBML
<compartment> element with the id 'compartment_0000001' has
multiple <annotation> children.
------------------

The interpretation of each line is as follows:

Validation id    : The name of the validation error or warning found.  
If from an SBML core specification, it will simply be that validation
number, and the same if it comes from libSBML itself and not a
specification (ids between 80000 and 99999).  If from a package, the
short name of the package will be used, i.e. "comp-10308".

Validation number: The number of the validation error or warning.
Will be the same as the validation id if from the core specifications
or from libSBML, and will be a greater number if from a package.
'comp' validation numbers are the validation id plus 1000000; 'fbc'
validation numbers are the validation id plus 2000000; 'qual'
validation numbers are the validation ids plus 3000000; and 'layout'
validation numbers are the validation id plus 6000000.

Severity         : Either 'Error' or 'Warning'.

Line number      : The line number of the XML file where the validation
problem was found.

Package          : The short name of the package the message comes
from.  Possibilities are 'comp', 'fbc', 'layout', 'qual' and '' (if
not from a package).

Short message    : A short description of the validation in question.

Full message     : A full description of the validation.  This is the
full text of the validation description from the specification it came
from, along with any specific information about how the model violated
that validation.  In the above example, "A given SBML object may
contain at most one <annotation> element.  Reference: L3V1 Section
3.2" is the text of the validation error in the SBML specification,
and "An SBML <compartment> element with the id 'compartment_0000001'
has multiple <annotation> children." is the message (generated by
libSBML) that explains how this particular model violated that
validation error.


6. INTERPRETATION OF THE FILE uniqueErrors.txt
======================================================================

The file "uniqueErrors.txt" in the folder "cases/syntactic/" contains
a list of every error represented by the test syntactic cases.


7. (LACK OF) INTEGRATION WITH THE REST OF THE SBML TEST SUITE
======================================================================

As of this writing, the syntactic tests are not integrated into the
SBML Test Suite Test Runner or the database of test results at
http://sbml.org/Facilities/Database.  The syntactic tests of the SBML
Test Suite are distributed in order to encourage developers to begin
investigating how they might be able to use these tests, but users and
developers need to develop their own approaches to running the tests
in software.


8. LICENSE AND DISTRIBUTION TERMS
======================================================================

For full license information, please refer to the file "LICENSE.txt"
in the parent directory.  Briefly, the test case distributions of the
SBML Test Suite are distributed under the terms of the LGPL; the
overall SBML Test Suite (including the software components) are
distributed under the LGPL but include components from other sources
licensed under other open-source terms.  (However, none of the terms
are more restrictive than the LGPL.)



     .-://///:`  .:/+++++/-`      .--.             `---`  `--
  -/++//:---:.`://+syyyssoo+`    ohhy`            /hhh.  -hy`
`/++/-`       ::/ohhyyssssoss-   ohhh+           .yhhh.  .hy`          
:++/.        `:::sysoo+++++oss.  ohoyh-         `ohoyh.  .hy`          
++//`        `--:/oo+///://+os:  oh//hs`        :hs.yh.  .hy`          
/+//.       `..--:////:--:/oos.  oh/`sh/       `yh-`yh.  .hy`          
`////:-.......---::://///++oo-   oh/ -hy.      +h+ `yh.  .hy`          
  .:///:::::--::::://///++oo:    oh/  +hs     -hy` `yh.  .hy`          
`::-``..--::::::://osyyysoooo.   oh/  `yh:   `sh:  `yh.  .hy`          
:o+/`      .:////oyhyyyyyyssss`  oh/   :hy`  /ho`  `yh.  .hy`          
/oo/        .///oyysoo+++oosyy-  oh/    oho .hh.   `yh.  .hy`          
.sso:       `+++oso+//////syyy`  oh/    .hh-oh/    `yh.  .hy`          
 :sss+-`   ./oooooo//:::+syyy.   oh/     /hhhs`    `yh.  -hy`          
  `/syssooossssssssssssyyyy/`    oh/      shh-     `yh.  -hhooooooooooo
    `-/+oso+/-.-:/osyyso/-`      -:.      .:-      `--`  `:::::::::::::




----------------------------------------------------------------------
The following is for [X]Emacs users.  Please leave in place.
Local Variables:
fill-column: 70
End:
----------------------------------------------------------------------
