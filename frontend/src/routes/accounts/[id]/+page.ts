import type { PageLoad } from '../../../../.svelte-kit/types/src/routes/accounts/[id]/$types';
import type {Account} from "$lib";

export const load: PageLoad = async ({ params }) => {
	const res = await fetch(`http://localhost:8080/api/accounts/${params.id}`);
	const account: Account = await res.json();

	return {
		account
	};
};
