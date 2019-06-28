package Cheminformatics.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point3d;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorEngine;
import org.openscience.cdk.qsar.IAtomicDescriptor;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.AtomDegreeDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.AtomHybridizationDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.AtomHybridizationVSEPRDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.AtomValenceDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.BondsToAtomDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.CovalentRadiusDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.DistanceToAtomDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.EffectiveAtomPolarizabilityDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.IPAtomicHOSEDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.IPAtomicLearningDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.InductiveAtomicHardnessDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.InductiveAtomicSoftnessDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.IsProtonInAromaticSystemDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.IsProtonInConjugatedPiSystemDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PartialPiChargeDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PartialSigmaChargeDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PartialTChargeMMFF94Descriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PartialTChargePEOEDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PeriodicTablePositionDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.PiElectronegativityDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.ProtonAffinityHOSEDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.ProtonTotalPartialChargeDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.RDFProtonDescriptor_G3R;
import org.openscience.cdk.qsar.descriptors.atomic.RDFProtonDescriptor_GDR;
import org.openscience.cdk.qsar.descriptors.atomic.RDFProtonDescriptor_GHR;
import org.openscience.cdk.qsar.descriptors.atomic.RDFProtonDescriptor_GHR_topol;
import org.openscience.cdk.qsar.descriptors.atomic.RDFProtonDescriptor_GSR;
import org.openscience.cdk.qsar.descriptors.atomic.SigmaElectronegativityDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.StabilizationPlusChargeDescriptor;
import org.openscience.cdk.qsar.descriptors.atomic.VdWRadiusDescriptor;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.qsar.result.IntegerArrayResult;
import org.openscience.cdk.qsar.result.IntegerResult;
import org.openscience.cdk.tools.HOSECodeGenerator;

@SuppressWarnings("deprecation")
public class GetAtomicDescriptors {
	

//	public static List<String> classNames = DescriptorEngine.getDescriptorClassNameByPackage("org.openscience.cdk.qsar.descriptors.atomic",
//            null);
//	public static  DescriptorEngine ENGINE = new DescriptorEngine(classNames, null);

	//	private static DescriptorEngine ENGINE = new DescriptorEngine(DescriptorEngine.ATOMIC);
	// Find nearest atom to all atoms in a molecule
	// 
	public static ArrayList<ArrayList<String>> getNearestAtoms(IAtomContainer mole) {
		
		ArrayList<ArrayList<String>> atom_distances = new ArrayList<ArrayList<String>>();

		int atomCount = mole.getAtomCount();

		List<IAtom> atoms = new ArrayList<IAtom>();
		for (int i = 0; i < atomCount; i++) {
			atoms.add(mole.getAtom(i));
		}

		for (int i = 0; i < atoms.size(); i++) {
			Double[] distances = new Double[atoms.size()];
				
			for (int j = 0; j < atoms.size(); j++) {
				if (j == i) {
						// Large number so that sorting puts it last
					distances[j] = 99999.12;
					continue;
				}
				
				Point3d firstPoint = atoms.get(i).getPoint3d();
				Point3d secondPoint = atoms.get(j).getPoint3d();
				Double distance = firstPoint.distance(secondPoint);
				distances[j] = distance;

			}

			// put the nearest atom at front
			ArrayList<String> indices = new ArrayList<String>();
			Double[] d = distances.clone(); // clone the original list (unsorted)
			Arrays.sort(d); // sort;
			List<Double> d_list = Arrays.asList(distances); // put into

			for (int j = 0; j < distances.length; j++) {
				String index = String.valueOf(d_list.indexOf(d[j])); // get index of that changed atoms
				indices.add(index);
			}

//			it return the nearest atom, not the actual distance
			atom_distances.add(indices);
				
			
			}
		return atom_distances;
	}
		
	public static ArrayList<Double> getAtomicDescriptor(IAtomContainer mole, List<IAtom> atoms) throws ClassNotFoundException, IOException{
		ArrayList<Double> values = new ArrayList<Double>();
		
		AtomDegreeDescriptor d1 = new AtomDegreeDescriptor();
		AtomHybridizationDescriptor	d2 = new AtomHybridizationDescriptor();
		AtomHybridizationVSEPRDescriptor d3 = new AtomHybridizationVSEPRDescriptor();
		AtomValenceDescriptor d4 = new AtomValenceDescriptor();
		BondsToAtomDescriptor d5 = new BondsToAtomDescriptor();
		CovalentRadiusDescriptor d6 = new CovalentRadiusDescriptor();
		DistanceToAtomDescriptor d7 = new DistanceToAtomDescriptor();
		EffectiveAtomPolarizabilityDescriptor d8 = new EffectiveAtomPolarizabilityDescriptor();
		InductiveAtomicHardnessDescriptor d9 = new InductiveAtomicHardnessDescriptor();
		InductiveAtomicSoftnessDescriptor d10 = new InductiveAtomicSoftnessDescriptor();
		IPAtomicHOSEDescriptor d11 = new IPAtomicHOSEDescriptor();
		IPAtomicLearningDescriptor d12 = new IPAtomicLearningDescriptor();
		IsProtonInAromaticSystemDescriptor d13 = new IsProtonInAromaticSystemDescriptor();
		IsProtonInConjugatedPiSystemDescriptor d14 = new IsProtonInConjugatedPiSystemDescriptor();
		PartialPiChargeDescriptor d15 = new PartialPiChargeDescriptor();
		PartialSigmaChargeDescriptor d16 = new PartialSigmaChargeDescriptor();
		PartialTChargeMMFF94Descriptor d17 = new PartialTChargeMMFF94Descriptor();
		PartialTChargePEOEDescriptor d18 = new PartialTChargePEOEDescriptor();
		PeriodicTablePositionDescriptor d19 = new PeriodicTablePositionDescriptor();
		PiElectronegativityDescriptor d20 = new PiElectronegativityDescriptor();
		ProtonAffinityHOSEDescriptor d21 = new ProtonAffinityHOSEDescriptor();
		ProtonTotalPartialChargeDescriptor d22 = new ProtonTotalPartialChargeDescriptor();
		RDFProtonDescriptor_G3R d23 = new RDFProtonDescriptor_G3R();
		RDFProtonDescriptor_GDR	d24 = new RDFProtonDescriptor_GDR();
		RDFProtonDescriptor_GHR d25 = new RDFProtonDescriptor_GHR();
		RDFProtonDescriptor_GHR_topol d26 = new RDFProtonDescriptor_GHR_topol();
		RDFProtonDescriptor_GSR d27 = new RDFProtonDescriptor_GSR();
		SigmaElectronegativityDescriptor d28 = new SigmaElectronegativityDescriptor();
		StabilizationPlusChargeDescriptor d29 = new StabilizationPlusChargeDescriptor();
		VdWRadiusDescriptor d30 = new VdWRadiusDescriptor();
//		add.calculate(atom, mole);
		
		for(int i = 0; i < atoms.size() ; i++) {
			IAtom atom = atoms.get(i);
			values.add(ParseIDescriptorResult(d1.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d2.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d3.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d4.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d5.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d6.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d7.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d8.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d9.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d10.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d11.calculate(atom, mole).getValue()));
//			values.add(ParseIDescriptorResult(d12.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d13.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d14.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d15.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d16.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d17.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d18.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d19.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d20.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d21.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d22.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d23.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d24.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d25.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d26.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d27.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d28.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d29.calculate(atom, mole).getValue()));
			values.add(ParseIDescriptorResult(d30.calculate(atom, mole).getValue()));
		}
		
		
		return values;
	}

	/**
	 * Calculate descriptors. Omits IPMolecularLearningDescriptor
	 *
	 * @param string
	 *            path to SDF input file
	 * @param string
	 *            path to CSV output file
	 * @param string
	 *            comma-seperated list of descriptor names (if empty, all
	 *            descriptors will be calculated)
	 */
	public static ArrayList<Double[]> getAtomicDescriptor(IAtomContainer mole, List<IAtom> atoms,String descNamesStr) throws java.io.IOException {
		System.out.println("number of atoms => " + atoms.size());
		List<String> classNames = DescriptorEngine.getDescriptorClassNameByPackage("org.openscience.cdk.qsar.descriptors.atomic",
	            null);
		DescriptorEngine ENGINE = new DescriptorEngine(classNames, null);
		List<IDescriptor> descriptors = ENGINE.getDescriptorInstances();
		List<String> descNames = Arrays.asList(descNamesStr.split(","));
		ArrayList<String> colNames = new ArrayList<String>();
		ArrayList<Double[]> values = new ArrayList<Double[]>();
		System.out.println("number of descriptors => " +descriptors.size());
		System.out.println("List<Double[]> values before => " + values.size());
		for (IDescriptor desc : descriptors) {
			System.out.println("inside IDescirptor loop");
			if (desc instanceof IPAtomicLearningDescriptor)
				continue;
			String tname = desc.getClass().getName();
			String[] tnamebits = tname.split("\\.");
			tname = tnamebits[tnamebits.length - 1];
			if ((descNamesStr.length() > 0) && (!descNames.contains(tname)))
				continue;
			String[] colNamesArr = desc.getDescriptorNames();
			for (int idx = 0; idx < colNamesArr.length; idx++) {
				colNamesArr[idx] = tname + "-" + colNamesArr[idx];
			}

			colNames.addAll(Arrays.asList(colNamesArr));
			List<Double[]> descriptor_value = computeDescriptorsAtomic(mole, atoms, (IAtomicDescriptor) desc);
			System.out.println("List<Double[]> descriptor_value => " + descriptor_value.size());
			values.addAll(descriptor_value);
			try {
				getHoseCodesForMolecule(mole); // return: ArrayList<String>
			}
			catch (Exception e){
				System.out.println(mole.getTitle());
			}
//			num_descriptor++;
				
		}
//		System.out.println("Number of Descriptors: " + num_descriptor); // 29
		System.out.println("value after => " + values.size());
		return values;
	}
	
	
	/**
	 * 
	 * @param res
	 * @return
	 */
	public static double ParseIDescriptorResult(IDescriptorResult res) {
		double output = 0.0;
		try {
			//System.out.println(res.toString());// res contain all the value for each atom
			if (res instanceof IntegerResult) {
				output = (double) ((IntegerResult) res).intValue();
//				 System.out.println("IntegerResult"+vv.get(0)[i]);
			} else if (res instanceof DoubleResult) {
				output = ((DoubleResult) res).doubleValue();
//				 System.out.println("DoubleResult"+vv.get(0)[i]);
			} else if (res instanceof DoubleArrayResult) {
				output = ((DoubleArrayResult) res).get(0);
//				 System.out.println("DoubleArrayResult"+vv.get(0)[i]);
			} else if (res instanceof IntegerArrayResult) {
				output = (double) ((IntegerArrayResult) res).get(0);
//				 System.out.println("IntegerArrayResult"+vv.get(0)[i]);
			} else if (res instanceof BooleanResult) {
				String result_bool = res.toString();
				if (result_bool == "false") {
					output = 0.0;
				} else {
					output = 1.0;
				}
			}else{
				throw new IllegalStateException();
			}
		}catch (Throwable e) {
//			System.err.println("Could not compute cdk feature " + descriptor);
//			e.printStackTrace();
			output = 0.0;
		}
		
		return output;
	}

	/*
	 * for computeListAtomic 
	 * Most important function (thank god, finally)
	 * the value for descriptor comes from this function call
	 * input: atomContainer mol; list of atoms; descriptors
	 * calculate each descriptor for each atoms (23 atoms)
	 */
	public static List<Double[]> computeDescriptorsAtomic(IAtomContainer mol, List<IAtom> atoms,
			IAtomicDescriptor descriptor) {
		List<Double[]> vv = new ArrayList<Double[]>();

//		// total 23 atoms (same as sdf file shows)

		vv.add(new Double[atoms.size()]);
		
		
		// iterate each atom and generate value for one descriptor type
		for (int i = 0; i < atoms.size(); i++) {
			if (atoms.get(i) == null) {
				vv.get(0)[i] = null;
			} else {
				try {
					IDescriptorResult res = descriptor.calculate(atoms.get(i), mol).getValue();
					//System.out.println(res.toString());// res contain all the value for each atom
					if (res instanceof IntegerResult) {
						vv.get(0)[i] = (double) ((IntegerResult) res).intValue();
//						 System.out.println("IntegerResult"+vv.get(0)[i]);
					} else if (res instanceof DoubleResult) {
						vv.get(0)[i] = ((DoubleResult) res).doubleValue();
//						 System.out.println("DoubleResult"+vv.get(0)[i]);
					} else if (res instanceof DoubleArrayResult) {
						vv.get(0)[i] = ((DoubleArrayResult) res).get(0);
//						 System.out.println("DoubleArrayResult"+vv.get(0)[i]);
					} else if (res instanceof IntegerArrayResult) {
						vv.get(0)[i] = (double) ((IntegerArrayResult) res).get(0);
//						 System.out.println("IntegerArrayResult"+vv.get(0)[i]);
					} else if (res instanceof BooleanResult) {
						String result_bool = res.toString();
						if (result_bool == "false") {
							vv.get(0)[i] = 0.0;
						} else {
							vv.get(0)[i] = 1.0;
						}
					}else{
						throw new IllegalStateException(
								"Unknown idescriptor result value for '" + descriptor + "' : " + res.getClass());
					}
				}catch (Throwable e) {
					System.err.println("Could not compute cdk feature " + descriptor);
					e.printStackTrace();
					vv.get(0)[i] = 0.0;
				}
			}
			
			if (vv.get(0)[i] != null && (vv.get(0)[i].isNaN() || vv.get(0)[i].isInfinite()))
				vv.get(0)[i] = 0.0;
		}
		
		
		return vv;
	}
	
	
	
	
	/**
	 * Get hose code from molecule
	 * @param mol
	 * @return
	 */
	public static ArrayList<String> getHoseCodesForMolecule(IAtomContainer mol) {
		HOSECodeGenerator hoseG = new HOSECodeGenerator();
		ArrayList<String> hoseCodes = new ArrayList<String>();

		int atomCount = mol.getAtomCount();
		for (int i = 0; i < atomCount; i++) {
			try {
				String hose = hoseG.getHOSECode(mol, mol.getAtom(i), 0);
				hoseCodes.add(hose);
//				System.out.println("HOSE = " + hose + "\n");
			} catch (CDKException e) {
				e.printStackTrace();
			}
		}
		return hoseCodes;
	}
}
